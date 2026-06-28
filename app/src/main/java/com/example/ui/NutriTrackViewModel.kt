package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.GeminiService
import com.example.data.AppDatabase
import com.example.data.MealLog
import com.example.data.MealRepository
import com.example.data.User
import com.example.data.FoodItem
import com.example.data.UserRepository
import com.example.data.FoodItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NutriTrackViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MealRepository
    val allMeals: StateFlow<List<MealLog>>

    // Session Management
    private val sharedPrefs = application.getSharedPreferences("nutritrack_prefs", Context.MODE_PRIVATE)
    private val _isLoggedIn = MutableStateFlow(sharedPrefs.getBoolean("is_logged_in", false))
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val userRepository: UserRepository
    private val foodItemRepository: FoodItemRepository

    // Local SQLite Food Items Catalogue Flow
    val localFoodItems: StateFlow<List<FoodItem>>

    // Calculated stats from database
    val totalCalories: StateFlow<Int>
    val totalProtein: StateFlow<Int>
    val totalCarbs: StateFlow<Int>
    val totalFat: StateFlow<Int>

    // Alerts
    private val _showExcessAlert = MutableStateFlow(false)
    val showExcessAlert = _showExcessAlert.asStateFlow()

    private val _showDeficitAlert = MutableStateFlow(false)
    val showDeficitAlert = _showDeficitAlert.asStateFlow()

    private val _healthInsight = MutableStateFlow("Memuat wawasan kesehatan Anda dari Gemini AI...")
    val healthInsight = _healthInsight.asStateFlow()

    private val _isGeneratingInsight = MutableStateFlow(false)
    val isGeneratingInsight = _isGeneratingInsight.asStateFlow()

    // Search and Catalogue state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Semua")
    val selectedCategory = _selectedCategory.asStateFlow()

    // Notification preferences state
    private val _hydrationNotifEnabled = MutableStateFlow(sharedPrefs.getBoolean("notif_hydration", true))
    val hydrationNotifEnabled = _hydrationNotifEnabled.asStateFlow()

    private val _mealNotifEnabled = MutableStateFlow(sharedPrefs.getBoolean("notif_meals", true))
    val mealNotifEnabled = _mealNotifEnabled.asStateFlow()

    private val _insightNotifEnabled = MutableStateFlow(sharedPrefs.getBoolean("notif_insights", true))
    val insightNotifEnabled = _insightNotifEnabled.asStateFlow()

    val defaultAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDOzcKaifVTFA6YcBZsTVW13IrrJEG9HFkm3PxOdpQLBke9KbXXAxRgJFY2BpG1QhE8Bz9FutDVW3gaIvAoQZqam7X_Nnvo9qvwwP3kDyPHS_ZSPvIizYLkO2P72GGeANffZWHFhSoeNsbj0Z_2Ra059ScIEuUZydtyhu30BHjxaydsCE7W0NsviK5MeLlJFBBZE_JuiRryRvsRVDcarQhzEjuGJdpdn7bgmKt-UM6YuevD79KYKk7DAzciD-fsZy8btNd3l_vIJfqY"

    private val _avatarUrl = MutableStateFlow(sharedPrefs.getString("avatar_url", defaultAvatarUrl) ?: defaultAvatarUrl)
    val avatarUrl = _avatarUrl.asStateFlow()

    fun updateAvatarUrl(url: String) {
        _avatarUrl.value = url
        sharedPrefs.edit().putString("avatar_url", url).apply()
    }

    fun setHydrationNotifEnabled(enabled: Boolean) {
        _hydrationNotifEnabled.value = enabled
        sharedPrefs.edit().putBoolean("notif_hydration", enabled).apply()
    }

    fun setMealNotifEnabled(enabled: Boolean) {
        _mealNotifEnabled.value = enabled
        sharedPrefs.edit().putBoolean("notif_meals", enabled).apply()
    }

    fun setInsightNotifEnabled(enabled: Boolean) {
        _insightNotifEnabled.value = enabled
        sharedPrefs.edit().putBoolean("notif_insights", enabled).apply()
    }

    fun triggerDemoNotification(title: String, message: String) {
        com.example.utils.NotificationHelper.sendNotification(getApplication(), title, message)
    }

    fun createCustomFoodItem(
        name: String,
        description: String,
        category: String,
        calories: Int,
        protein: Int,
        carbs: Int,
        fat: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (name.isBlank()) {
            onError("Nama makanan tidak boleh kosong.")
            return
        }
        viewModelScope.launch {
            try {
                val newItem = FoodItem(
                    id = "custom_" + java.util.UUID.randomUUID().toString(),
                    name = name,
                    description = description.ifBlank { "Makanan buatan pengguna." },
                    category = category,
                    calories = calories,
                    protein = protein,
                    carbs = carbs,
                    fat = fat,
                    imageUrl = URL_COFFEE,
                    isPopular = false,
                    rating = 5.0f
                )
                withContext(Dispatchers.IO) {
                    foodItemRepository.insertFoodItem(newItem)
                }
                
                if (mealNotifEnabled.value) {
                    com.example.utils.NotificationHelper.sendNotification(
                        getApplication(),
                        "Menu Kustom Ditambahkan! 🍽️",
                        "'$name' berhasil ditambahkan ke katalog pribadi Anda."
                    )
                }
                
                onSuccess()
            } catch (e: Exception) {
                onError("Gagal menambahkan item: ${e.message}")
            }
        }
    }

    // Dynamic target limits based on logged-in user
    val targetCalories: Int
        get() = currentUser.value?.targetCalories ?: 2500
    val targetProtein = 150
    val targetCarbs = 300
    val targetFat = 80

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MealRepository(database.mealDao())
        userRepository = UserRepository(database.userDao())
        foodItemRepository = FoodItemRepository(database.foodItemDao())

        allMeals = repository.allMeals.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        localFoodItems = foodItemRepository.allFoodItems.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        totalCalories = allMeals.map { meals -> meals.sumOf { it.calories } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

        totalProtein = allMeals.map { meals -> meals.sumOf { it.protein } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

        totalCarbs = allMeals.map { meals -> meals.sumOf { it.carbs } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

        totalFat = allMeals.map { meals -> meals.sumOf { it.fat } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

        // Seed users and food items, and load session on start
        viewModelScope.launch {
            seedDatabase()
            loadUserSession()
        }

        // Seed mock data if database is empty on start (Commented out to keep initial food empty on launch as per request)
        /*
        viewModelScope.launch {
            allMeals.collect { meals ->
                if (meals.isEmpty()) {
                    seedMockData()
                }
            }
        }
        */

        // Generate Health Insight when meals are updated
        viewModelScope.launch {
            allMeals.collect { meals ->
                if (meals.isNotEmpty()) {
                    generateInsightFromMeals(meals)
                } else {
                    _healthInsight.value = "Belum ada makanan yang dicatat hari ini. Mulai catat sarapan atau makan siang Anda untuk mendapatkan analisis nutrisi cerdas!"
                }
            }
        }
    }

    private suspend fun seedDatabase() {
        withContext(Dispatchers.IO) {
            // Seed default user if not exists
            val anyUser = userRepository.getAnyUser()
            if (anyUser == null) {
                userRepository.insertUser(
                    User(
                        email = "amin13dpk@gmail.com",
                        name = "Al Amin Abdilah",
                        targetCalories = 2500,
                        password = "password123"
                    )
                )
            }

            // Seed and synchronize local food items catalog from Kotlin CATALOGUE list
            val dbFoods = CATALOGUE.map { item ->
                FoodItem(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    category = item.category,
                    calories = item.calories,
                    protein = item.protein,
                    carbs = item.carbs,
                    fat = item.fat,
                    imageUrl = item.imageUrl,
                    isPopular = item.isPopular,
                    rating = item.rating
                )
            }
            foodItemRepository.insertFoodItems(dbFoods)
        }
    }

    private suspend fun loadUserSession() {
        val loggedIn = sharedPrefs.getBoolean("is_logged_in", false)
        if (loggedIn) {
            val email = sharedPrefs.getString("logged_in_email", "amin13dpk@gmail.com") ?: "amin13dpk@gmail.com"
            val user = userRepository.getUserByEmail(email)
            if (user != null) {
                _currentUser.value = user
                _isLoggedIn.value = true
            } else {
                // Prefs state mismatch
                sharedPrefs.edit().clear().apply()
                _isLoggedIn.value = false
            }
        }
    }

    fun login(email: String, password: String, keepMeLoggedIn: Boolean, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                userRepository.getUserByEmail(email)
            }
            if (user != null && user.password == password) {
                sharedPrefs.edit().apply {
                    putBoolean("is_logged_in", keepMeLoggedIn)
                    putString("logged_in_email", email)
                    apply()
                }
                _currentUser.value = user
                _isLoggedIn.value = true
                onSuccess()
            } else {
                onError("Email atau password tidak valid.")
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            // Clear credentials
            sharedPrefs.edit().clear().apply()
            _isLoggedIn.value = false
            _currentUser.value = null

            // Reset meal log in database
            withContext(Dispatchers.IO) {
                repository.clearAll()
            }

            // Clear alerts & insights
            _showExcessAlert.value = false
            _showDeficitAlert.value = false
            _healthInsight.value = "Belum ada makanan yang dicatat hari ini."
            _avatarUrl.value = defaultAvatarUrl

            onSuccess()
        }
    }

    fun register(name: String, email: String, password: String, goalKcal: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val existing = withContext(Dispatchers.IO) {
                userRepository.getUserByEmail(email)
            }
            if (existing != null) {
                onError("Email sudah terdaftar.")
            } else {
                val newUser = User(
                    email = email,
                    name = name,
                    targetCalories = goalKcal,
                    password = password
                )
                withContext(Dispatchers.IO) {
                    userRepository.insertUser(newUser)
                }
                onSuccess()
            }
        }
    }

    fun updatePersonalInfo(newEmail: String, newPassword: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val oldUser = _currentUser.value
        if (oldUser == null) {
            onError("User belum masuk.")
            return
        }
        val oldEmail = oldUser.email

        viewModelScope.launch {
            if (newEmail.isBlank() || newPassword.isBlank()) {
                onError("Email dan kata sandi tidak boleh kosong.")
                return@launch
            }
            if (newEmail != oldEmail) {
                val existing = withContext(Dispatchers.IO) {
                    userRepository.getUserByEmail(newEmail)
                }
                if (existing != null) {
                    onError("Email baru sudah terdaftar oleh pengguna lain.")
                    return@launch
                }
            }

            try {
                withContext(Dispatchers.IO) {
                    if (newEmail != oldEmail) {
                        userRepository.deleteUserByEmail(oldEmail)
                    }
                    val updatedUser = User(
                        email = newEmail,
                        name = oldUser.name,
                        targetCalories = oldUser.targetCalories,
                        password = newPassword
                    )
                    userRepository.insertUser(updatedUser)
                    _currentUser.value = updatedUser
                    sharedPrefs.edit().putString("logged_in_email", newEmail).apply()
                }
                
                if (mealNotifEnabled.value) {
                    com.example.utils.NotificationHelper.sendNotification(
                        getApplication(),
                        "Informasi Personal Diperbarui! 👤",
                        "Data akun Anda berhasil diperbarui di sistem NutriTrack."
                    )
                }
                
                onSuccess()
            } catch (e: Exception) {
                onError("Gagal memperbarui informasi: ${e.message}")
            }
        }
    }

    private suspend fun seedMockData() {
        withContext(Dispatchers.IO) {
            repository.clearAll()
            // Perfect 1,200 kcal seed matching screenshot precisely:
            // Breakfast (420 kcal, 30g Protein, 50g Carbs, 10g Fat)
            repository.insert(
                MealLog(
                    name = "Greek Yogurt dengan Beri",
                    calories = 415,
                    protein = 30,
                    carbs = 49,
                    fat = 10,
                    mealType = "Sarapan"
                )
            )
            repository.insert(
                MealLog(
                    name = "Kopi Hitam",
                    calories = 5,
                    protein = 0,
                    carbs = 1,
                    fat = 0,
                    mealType = "Sarapan"
                )
            )
            // Lunch (780 kcal, 55g Protein, 70g Carbs, 35g Fat)
            repository.insert(
                MealLog(
                    name = "Chicken Caesar Salad",
                    calories = 650,
                    protein = 45,
                    carbs = 45,
                    fat = 30,
                    mealType = "Makan Siang"
                )
            )
            repository.insert(
                MealLog(
                    name = "Teh Manis Hangat",
                    calories = 130,
                    protein = 10,
                    carbs = 25,
                    fat = 5,
                    mealType = "Makan Siang"
                )
            )
        }
    }

    fun resetToMockData() {
        viewModelScope.launch {
            seedMockData()
            _showExcessAlert.value = false
            _showDeficitAlert.value = false
        }
    }

    fun clearAllMeals() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.clearAll()
            }
            _showExcessAlert.value = false
            _showDeficitAlert.value = false
        }
    }

    fun logMeal(name: String, calories: Int, protein: Int, carbs: Int, fat: Int, mealType: String) {
        viewModelScope.launch {
            val currentTotal = totalCalories.value
            val newTotal = currentTotal + calories

            withContext(Dispatchers.IO) {
                repository.insert(
                    MealLog(
                        name = name,
                        calories = calories,
                        protein = protein,
                        carbs = carbs,
                        fat = fat,
                        mealType = mealType
                    )
                )
            }

            // Check for Excess Alert
            if (newTotal > targetCalories) {
                _showExcessAlert.value = true
            }
        }
    }

    fun deleteMeal(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteById(id)
            }
            // Check if current total drops below limit
            if (totalCalories.value <= targetCalories) {
                _showExcessAlert.value = false
            }
        }
    }

    fun setQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun dismissExcessAlert() {
        _showExcessAlert.value = false
    }

    fun dismissDeficitAlert() {
        _showDeficitAlert.value = false
    }

    fun simulateDeficitCheck() {
        viewModelScope.launch {
            val currentTotal = totalCalories.value
            if (currentTotal < 1800) {
                _showDeficitAlert.value = true
                if (insightNotifEnabled.value) {
                    com.example.utils.NotificationHelper.sendNotification(
                        getApplication(),
                        "Peringatan Defisit Kalori! ⚠️",
                        "Asupan kalori harian Anda baru mencapai $currentTotal kcal (target: 2500 kcal). Silakan makan atau catat hidangan berikutnya!"
                    )
                }
            } else {
                _showDeficitAlert.value = false
                com.example.utils.NotificationHelper.sendNotification(
                    getApplication(),
                    "Status Kalori Optimal! 🎉",
                    "Asupan kalori harian Anda sudah mencapai $currentTotal kcal. Nutrisi Anda tercukupi dengan baik!"
                )
            }
        }
    }

    private fun generateInsightFromMeals(meals: List<MealLog>) {
        viewModelScope.launch {
            _isGeneratingInsight.value = true
            val mealsText = meals.joinToString("\n") {
                "- ${it.mealType}: ${it.name} (${it.calories} kcal, P: ${it.protein}g, K: ${it.carbs}g, L: ${it.fat}g)"
            }
            val insight = withContext(Dispatchers.IO) {
                GeminiService.getHealthInsight(mealsText)
            }
            _healthInsight.value = insight
            _isGeneratingInsight.value = false
        }
    }

    // Force regenerate health insight manually
    fun refreshInsight() {
        val meals = allMeals.value
        if (meals.isNotEmpty()) {
            generateInsightFromMeals(meals)
        }
    }
}
