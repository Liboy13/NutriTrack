package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.data.MealLog
import com.example.ui.theme.*
import com.example.ui.theme.ProgressGreen
import com.example.ui.theme.WarningRed

// Image URL Constants matching screenshot
const val URL_AVATAR = "https://lh3.googleusercontent.com/aida-public/AB6AXuDOzcKaifVTFA6YcBZsTVW13IrrJEG9HFkm3PxOdpQLBke9KbXXAxRgJFY2BpG1QhE8Bz9FutDVW3gaIvAoQZqam7X_Nnvo9qvwwP3kDyPHS_ZSPvIizYLkO2P72GGeANffZWHFhSoeNsbj0Z_2Ra059ScIEuUZydtyhu30BHjxaydsCE7W0NsviK5MeLlJFBBZE_JuiRryRvsRVDcarQhzEjuGJdpdn7bgmKt-UM6YuevD79KYKk7DAzciD-fsZy8btNd3l_vIJfqY"
const val URL_YOGURT = "https://lh3.googleusercontent.com/aida-public/AB6AXuCuzqg60Hcqugxe-_e_6z39KV8EUQZhWkoc1ZAUv-I2sW21UB-6YEGeto7FRxePcTP8vAdFxcruV6sXlPUzdM39OWgkL5zS7PZL1DnsIyieXprz4W45RjgbWvaA0uwqaDlmFD7ZG0On1ikHWcXBC7Fhwn5k8tyYyXJGGUeiOuJPyco6y4gW5hk-bmMohj50zzukHBOWq7Fyu7-UQ39hzjXBoi0U3-yIam9zlj4ZBfm2-Mo9HVBOll3zGAExVu39GotwvzLr7UhWra-z"
const val URL_COFFEE = "https://lh3.googleusercontent.com/aida-public/AB6AXuDQdr1lZ2H6rIOy66z44m0HZ05T315On-NxSRb8qTx_ajQHWwceQgdisRrIWT8_hdQRZzvyrdVWUsfCmekBeu6EXcq2AzLUW0r_PUblrbC4eaXgLGN1VUGqJiwpVpXmQyQN8HqQ0M0cmlIBM4HiltOdoFwmU25gU0mqFjSNqBoiT36tYR7KUOYjORda_I9Rk87jY0UyEO83OAgPRO4FiHwy_nquHEz_A65rY3yMCOqvlpeO2sngxSquTJoS8HDKLj1rN7sfCeSxrL0Y"
const val URL_SALAD = "https://lh3.googleusercontent.com/aida-public/AB6AXuDYgYAtFVJIS1872ggi6jDXcV3JhysSH5t_V90yupbb99GWdsXjJlk2lJac7o7EQrDp83oOujEb1yNv9Z6t5OG7feqd3sshqJ9k9pMfv1nPkg1X_2oA4cSq5Ph3GtV6L1Rz-tnx2po8AOD1vPzq439t8dLSieJSufXBt4nqekDAICwdaHQ_6DSnwhkpG74P5Y9i8yxvHIlaVR4p4lMKYd4IYjw3wNcyYOGcYErEZJeh3_D7DFndOWQG1_bTORy257_X5knvCAJnlmtR"
const val URL_NASI_PADANG_BASE = "https://lh3.googleusercontent.com/aida-public/AB6AXuC2Oo-beGNJODQuCSa5cn9mOimgplOn_HlJzO0fz4jUDWEoort61aul_AMPpMGsFxRXj0MWDsstZ9apC8H7du-L7OXL4Odf75dKVPbpwBivPMb3UtQg-eD_7x-bWrVq2eR_MSq5RkbX4GIuMkYwyi_u1B6pwJ9n8KA89rptEdXsT3XxBSuezk1mNqPUctbfaWkpNXb31twgS857yTnbKfEiOlZiBAbtjygCLrqmt_-l3j1uHdk94etlxZ-731d862yln_V8S4P-KZ9c"
const val URL_NASI_PADANG_FULL = "https://lh3.googleusercontent.com/aida-public/AB6AXuCrifH2tQqFmcFVG7I5ITMkng9A10p8KOHEp-EKYcAvg6uOJuZ9sDoLe5NO9kmW1stfjv5gp87t53I3SATu2NzSaXB5F-QVaw0aMhj1kLL58UO5WGD7LkfUg5hm1LTq2x5xIM_uYq-d1tbLQ2C5slHA3CCFuBCj0KZBNTN9-7j_U2gvozexSwcCxL4GWV01z3Zehtl9TEEg7oGEfSkLHI0qkp_zm1FGe2UtD1QHbDf2PnxdBTvtbrvyHLzS5VM42Bpcey5SdU2ghubz"
const val URL_RENDANG = "https://lh3.googleusercontent.com/aida-public/AB6AXuDeuM5xB8vSRzT-0ohqPfIdHd_66bKb6YzTbcQVWUgwVxnMlVRBdo7MKz2Ggf3am79caUpEs5q59nj5P678v1bsFRVKNjPmLQeGAzgIp5dJe0pKUiUcBrmf0i-LuNj0EQ3wHodizBhV7KJZgKLmsx9cN7k8lnLk4KuVmmzGRJjhtJmC-xVuc1d_ByZpUpgNLMvu3R6W7YKGnKFWImRL-ggs_jOvEFg293Q7A8p5H2qBdWAav2t85VskO2iaVQjxXQrn7c9ka7ZQk9gV"
const val URL_IKAN_BAKAR = "https://lh3.googleusercontent.com/aida-public/AB6AXuA6t_yO9FXPESeYn3ezmH4zNRlhNeq1Wdhlm4r-AwdmtDPTEP7cpRx979NUSO45K5pvVqauy6uJycMMC3NQ9KgGUXt-E8P4CrSTJVOczyU7PS8cD4T0zpZoJhBQp9ATnPecoiT1LXkurLf5UFLz8H4P6DCqaiBxCj7tfiHoQ5sGsaduJaqO4h-DcgTOdJmILvHyZFcAtdHbG0EJvLN5L6BP7dbSNXgNJgRfS44sfeNcLkHgPWfjUIxy92Y7hAWFvjwK2Cunv4m_I9Lz"
const val URL_AYAM_POP = "https://lh3.googleusercontent.com/aida-public/AB6AXuDZcNVSvEdz8lbUbRx4uXgQF8dJ6WVv1J_4r2DA8yo8XMCI0dlZ7O5fj_MKup2F0F70jo5W6nVUjntizc5DaGuT6Oxzs-btyWgPiTIJ34bDSbkCJAD4lND_pjzSuuSHXJJzl60GxpjLOUX8caRJ2xq-EvESTPAxXZK8AUYj3rdR_MMZzgHq6nonKFcPVUqM8ZK0rC5J2aqz-0A0Gjhxs4oQDiImuBQq3c_5h_mFyYJAvKfdHGKnP_hgZGgw1GtOX-_vqzfchFmQgz9f"
const val URL_SAYUR_NANGKA = "https://lh3.googleusercontent.com/aida-public/AB6AXuBNxUSSXuNyoPysvq6xHIFSpIrFGXtOW1F4nQ9-UPlcNPldkal0mx280MjWdxs81ML_48nCfRG8O2k9Xlgcj3UiasQJaFMIFDhyo1ubqRuL3-zFL_YI_V8eho6ZP9Fx2g7YLzlg9imxnSjP4oQfMnrcmP4WbfvhYQPeioyBoZ4TegxAUsiDs3e2iruIZjEuK2TxPtJhUIG9EL1LI5diBOUL2vbis_9fDoPZDfbrP1IaEpygtATFFFUr1hozqmVAOFctu4MKV-FZt3xU"
const val URL_SATE_AYAM = "https://lh3.googleusercontent.com/aida-public/AB6AXuAhc8H6mnLqu2xQAlb7VGIBjGe5RYIPE_6iIZ7dbnVLiCHTHVk-uHJJrOVWhwh-fbcN0-DF07ngNFPtbLj_eC3Y0xGz9--k5mLrK1zLo7G1jqU7ocyJFZFWdwRroai31LPVSS6B2N3GeZS5ZgxKf6SiY09qd4Gm8bqvO_YpZBV0W_VHxpj62F9Ew029fMn9TK9-sOIG6t65yigUQvMJhoZvuxii2xtSxVswuGFfPrkH1FPe6QSAZDP8n0cJjwCRnceyK4dFwoPt6_TI"
const val URL_GADO_GADO = "https://lh3.googleusercontent.com/aida-public/AB6AXuDZo6DgcYCt5SlUeN1O0uzwPh2XPs6xG51FVIRzHdAvsP7-d_1aaTLTaUlogvXFy8ATvdOpRCm8uagZd_zdDATY9MScKz4hRzLggmcp9PaS-od2f35YJrLuSgUbv0SOTvrv7UKXj0aSm-B-RCL06VQ6L0neVVEme_HOhsX7yyGg0amNVeMOJGO3ZT71qwt8ev2RMj77Mcg2mmunsoUl1Xag4SNGUr482EaUW0Ks8GajnonqQnJ2KCgG1QU5uldarVITcIdYh-9DqNgl"

data class CatalogueItem(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val imageUrl: String,
    val isPopular: Boolean = false,
    val rating: Float = 4.5f
)

val CATALOGUE = listOf(
    CatalogueItem(
        id = "nasi_padang",
        name = "Nasi Padang",
        description = "Porsi standar: Nasi, Rendang Sapi, Sayur Singkong, dan Sambal Ijo.",
        category = "Indonesia",
        calories = 645,
        protein = 40,
        carbs = 80,
        fat = 18,
        imageUrl = URL_NASI_PADANG_FULL,
        isPopular = true,
        rating = 4.8f
    ),
    CatalogueItem(
        id = "sate_ayam",
        name = "Sate Ayam (10 tusuk)",
        description = "Sate daging ayam panggang empuk disiram bumbu kacang gurih.",
        category = "Indonesia",
        calories = 420,
        protein = 30,
        carbs = 15,
        fat = 25,
        imageUrl = URL_SATE_AYAM,
        rating = 4.5f
    ),
    CatalogueItem(
        id = "gado_gado",
        name = "Gado-Gado",
        description = "Sayuran rebus segar, telur rebus, tahu goreng, saus kacang khas Jawa.",
        category = "Indonesia",
        calories = 310,
        protein = 12,
        carbs = 35,
        fat = 15,
        imageUrl = URL_GADO_GADO,
        rating = 4.9f
    ),
    CatalogueItem(
        id = "rendang_portion",
        name = "Beef Rendang (Portion)",
        description = "Daging sapi premium yang dimasak lambat dengan bumbu karamel kelapa.",
        category = "Indonesia",
        calories = 250,
        protein = 22,
        carbs = 5,
        fat = 16,
        imageUrl = URL_RENDANG,
        rating = 4.9f
    ),
    CatalogueItem(
        id = "caesar_salad",
        name = "Chicken Caesar Salad",
        description = "Dada ayam panggang, selada romaine renyah, keju parmesan parut, saus Caesar.",
        category = "Western",
        calories = 650,
        protein = 45,
        carbs = 45,
        fat = 30,
        imageUrl = URL_SALAD,
        rating = 4.6f
    ),
    CatalogueItem(
        id = "black_coffee",
        name = "Kopi Hitam",
        description = "Secangkir kopi robusta hitam pekat tanpa gula tambahan.",
        category = "Minuman",
        calories = 5,
        protein = 0,
        carbs = 1,
        fat = 0,
        imageUrl = URL_COFFEE,
        rating = 4.7f
    ),
    CatalogueItem(
        id = "teh_manis",
        name = "Teh Manis Hangat",
        description = "Seduhan teh melati wangi dengan gula tebu asli.",
        category = "Minuman",
        calories = 130,
        protein = 10,
        carbs = 25,
        fat = 5,
        imageUrl = URL_COFFEE,
        rating = 4.4f
    ),
    CatalogueItem(
        id = "greek_yogurt",
        name = "Greek Yogurt dengan Beri",
        description = "Yogurt Yunani kental disajikan dengan buah beri segar dan granola.",
        category = "Sehat",
        calories = 415,
        protein = 30,
        carbs = 49,
        fat = 10,
        imageUrl = URL_YOGURT,
        rating = 4.8f
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriTrackApp(viewModel: NutriTrackViewModel) {
    val navController = rememberNavController()

    val totalCalories by viewModel.totalCalories.collectAsState()
    val totalProtein by viewModel.totalProtein.collectAsState()
    val totalCarbs by viewModel.totalCarbs.collectAsState()
    val totalFat by viewModel.totalFat.collectAsState()

    val showExcessAlert by viewModel.showExcessAlert.collectAsState()
    val showDeficitAlert by viewModel.showDeficitAlert.collectAsState()

    var targetMealTypeForQuickAdd by remember { mutableStateOf("Makan Siang") }

    // Excess Calorie Warning dialog
    if (showExcessAlert) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissExcessAlert() },
            icon = { Icon(Icons.Default.Warning, contentDescription = "Peringatan", tint = WarningRed) },
            title = { Text("Peringatan Kalori", fontWeight = FontWeight.Bold) },
            text = { Text("Batas kalori harian terlampaui! Perhatikan aktivitas fisik Anda hari ini.") },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissExcessAlert() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Paham")
                }
            }
        )
    }

    // Deficit Warning dialog (20:00 simulation)
    if (showDeficitAlert) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDeficitAlert() },
            icon = { Icon(Icons.Default.Info, contentDescription = "Peringatan Defisit", tint = ProgressGreen) },
            title = { Text("Peringatan Kekurangan Kalori", fontWeight = FontWeight.Bold) },
            text = { Text("Asupan kalori Anda hari ini masih kurang. Jangan lupa makan malam untuk energi esok hari!") },
            confirmButton = {
                Button(
                    onClick = { viewModel.dismissDeficitAlert() },
                    colors = ButtonDefaults.buttonColors(containerColor = ProgressGreen)
                ) {
                    Text("Siap, Makan Malam!")
                }
            }
        )
    }

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn && currentRoute != "login") {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (isLoggedIn && currentRoute != "login") {
                NutriTrackBottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn) "home" else "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToSearch = { mealType ->
                        targetMealTypeForQuickAdd = mealType
                        navController.navigate("search")
                    },
                    onNavigateToCustomization = {
                        navController.navigate("customization")
                    }
                )
            }
            composable("search") {
                SearchScreen(
                    viewModel = viewModel,
                    defaultMealType = targetMealTypeForQuickAdd,
                    onNavigateToCustomization = {
                        navController.navigate("customization")
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable("customization") {
                CustomizationScreen(
                    viewModel = viewModel,
                    targetMealType = targetMealTypeForQuickAdd,
                    onLoggedAndBack = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable("profile") {
                ProfileScreen(
                    viewModel = viewModel,
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NutriTrackBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        modifier = Modifier.testTag("bottom_nav_bar")
    ) {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = {
                if (currentRoute != "home") {
                    navController.navigate("home") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Beranda") },
            label = { Text("Beranda", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
            ),
            modifier = Modifier.testTag("home_tab")
        )
        NavigationBarItem(
            selected = currentRoute == "search",
            onClick = {
                if (currentRoute != "search") {
                    navController.navigate("search") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.Search, contentDescription = "Cari") },
            label = { Text("Cari", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
            ),
            modifier = Modifier.testTag("search_tab")
        )
        NavigationBarItem(
            selected = currentRoute == "customization",
            onClick = {
                if (currentRoute != "customization") {
                    navController.navigate("customization") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Kustom") },
            label = { Text("Kustom", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
            ),
            modifier = Modifier.testTag("custom_tab")
        )
        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = {
                if (currentRoute != "profile") {
                    navController.navigate("profile") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profil") },
            label = { Text("Profil", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
            ),
            modifier = Modifier.testTag("profile_tab")
        )
    }
}

@Composable
fun HomeScreen(
    viewModel: NutriTrackViewModel,
    onNavigateToSearch: (String) -> Unit,
    onNavigateToCustomization: () -> Unit
) {
    val meals by viewModel.allMeals.collectAsState()
    val totalCalories by viewModel.totalCalories.collectAsState()
    val totalProtein by viewModel.totalProtein.collectAsState()
    val totalCarbs by viewModel.totalCarbs.collectAsState()
    val totalFat by viewModel.totalFat.collectAsState()

    val currentUser by viewModel.currentUser.collectAsState()
    val userName = currentUser?.name ?: "Al Amin Abdilah"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .testTag("home_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "NutriTrack",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = (-0.5).sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Halo, $userName",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                            color = OnSurfaceVariantText
                        )
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFEF3C7), RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "PREMIUM",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB45309),
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
                IconButton(
                    onClick = { /* Simulated trigger */ },
                    modifier = Modifier
                        .background(
                            ContainerLow,
                            CircleShape
                        )
                        .testTag("btn_notifications")
                ) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notifikasi",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Daily Progress Circle Card (Side-by-side design)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, OutlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val remaining = (viewModel.targetCalories - totalCalories).coerceAtLeast(0)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(130.dp)
                    ) {
                        // Background ring
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawCircle(
                                color = OutlineVariant,
                                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                            )
                        }

                        // Progress ring
                        val sweepAngle = (totalCalories.toFloat() / viewModel.targetCalories.toFloat()) * 360f
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawArc(
                                color = PrimaryBlue,
                                startAngle = -90f,
                                sweepAngle = sweepAngle.coerceAtMost(360f),
                                useCenter = false,
                                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = String.format("%,d", remaining),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurfaceText
                            )
                            Text(
                                text = "Kcal Sisa",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurfaceVariantText,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Target Harian",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = OnSurfaceVariantText
                            )
                            Text(
                                text = "${viewModel.targetCalories} kcal",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurfaceText
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Telah Dimakan",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = ProgressGreen
                            )
                            Text(
                                text = "$totalCalories kcal",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = ProgressGreen
                            )
                        }
                    }
                }
            }
        }

        // Macronutrients horizontal bars Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, OutlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Asupan Makronutrisi",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceText
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Protein
                    MacroProgressRow(
                        label = "PROTEIN",
                        current = totalProtein,
                        target = viewModel.targetProtein,
                        color = PrimaryBlue
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    // Carbs
                    MacroProgressRow(
                        label = "KARBOHIDRAT",
                        current = totalCarbs,
                        target = viewModel.targetCarbs,
                        color = ProgressOrange
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    // Fat
                    MacroProgressRow(
                        label = "LEMAK",
                        current = totalFat,
                        target = viewModel.targetFat,
                        color = ProgressGreen
                    )
                }
            }
        }

        // Protein progress/Great Pace motivational Banner
        item {
            val proteinPercent = (totalProtein.toFloat() / viewModel.targetProtein.toFloat() * 100).toInt()
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        val firstName = userName.split(" ").firstOrNull() ?: "Al Amin"
                        Text(
                            text = "Kerja bagus, $firstName!",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Kamu sudah mencapai $proteinPercent% target protein harianmu. Pertahankan!",
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Today's Meals Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Catatan Makanan Hari Ini",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "RIWAYAT",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* History trigger */ }
                )
            }
        }

        // Meal Slots
        val breakfastMeals = meals.filter { it.mealType == "Sarapan" }
        val lunchMeals = meals.filter { it.mealType == "Makan Siang" }
        val dinnerMeals = meals.filter { it.mealType == "Makan Malam" }

        // Breakfast
        item {
            MealSlotCard(
                title = "SARAPAN",
                meals = breakfastMeals,
                onAddClick = { onNavigateToSearch("Sarapan") },
                onDeleteClick = { id -> viewModel.deleteMeal(id) }
            )
        }

        // Lunch
        item {
            MealSlotCard(
                title = "MAKAN SIANG",
                meals = lunchMeals,
                onAddClick = { onNavigateToSearch("Makan Siang") },
                onDeleteClick = { id -> viewModel.deleteMeal(id) }
            )
        }

        // Dinner / Empty State placeholder
        item {
            if (dinnerMeals.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Makan Malam",
                            tint = Color.Gray,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Makan Malam",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Anda belum mencatat makan malam hari ini.",
                            fontSize = 13.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { onNavigateToSearch("Makan Malam") },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Text("CATAT MAKAN MALAM", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            } else {
                MealSlotCard(
                    title = "MAKAN MALAM",
                    meals = dinnerMeals,
                    onAddClick = { onNavigateToSearch("Makan Malam") },
                    onDeleteClick = { id -> viewModel.deleteMeal(id) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun MacroProgressRow(label: String, current: Int, target: Int, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label, 
                fontSize = 11.sp, 
                fontWeight = FontWeight.Bold, 
                color = OnSurfaceVariantText,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "${current}g / ${target}g", 
                fontSize = 13.sp, 
                fontWeight = FontWeight.Bold,
                color = OnSurfaceText
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        val progress = if (target > 0) current.toFloat() / target.toFloat() else 0f
        LinearProgressIndicator(
            progress = { progress.coerceAtMost(1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape),
            color = color,
            trackColor = ContainerLow
        )
    }
}

@Composable
fun MealSlotCard(
    title: String,
    meals: List<MealLog>,
    onAddClick: () -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, OutlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            // Card slot header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ContainerLow)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title, 
                    fontWeight = FontWeight.Bold, 
                    fontSize = 12.sp, 
                    color = OnSurfaceText,
                    letterSpacing = 0.5.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val totalSlotKcal = meals.sumOf { it.calories }
                    Text(
                        text = "$totalSlotKcal kcal",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = OnSurfaceText
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    IconButton(
                        onClick = onAddClick,
                        modifier = Modifier
                            .size(24.dp)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah Makanan",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Items list
            if (meals.isEmpty()) {
                Text(
                    text = "Belum ada asupan dicatat.",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    meals.forEach { meal ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Find corresponding image for mock visuals
                            val matchingImage = when {
                                meal.name.contains("Yogurt", true) -> URL_YOGURT
                                meal.name.contains("Kopi", true) -> URL_COFFEE
                                meal.name.contains("Salad", true) -> URL_SALAD
                                meal.name.contains("Teh", true) -> URL_COFFEE
                                meal.name.contains("Padang", true) -> URL_NASI_PADANG_FULL
                                meal.name.contains("Rendang", true) -> URL_RENDANG
                                meal.name.contains("Sate", true) -> URL_SATE_AYAM
                                meal.name.contains("Gado", true) -> URL_GADO_GADO
                                else -> URL_NASI_PADANG_BASE
                            }

                            AsyncImage(
                                model = matchingImage,
                                contentDescription = meal.name,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(meal.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                Text(
                                    text = "P: ${meal.protein}g | K: ${meal.carbs}g | L: ${meal.fat}g",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                            Text(
                                "${meal.calories}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            IconButton(onClick = { onDeleteClick(meal.id) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Hapus",
                                    tint = WarningRed.copy(alpha = 0.7f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreen(
    viewModel: NutriTrackViewModel,
    defaultMealType: String,
    onNavigateToCustomization: () -> Unit,
    onBack: () -> Unit
) {
    val query by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val categories = listOf("Semua", "Indonesia", "Western", "Minuman", "Sehat")

    val filteredItems = CATALOGUE.filter { item ->
        val matchesQuery = item.name.contains(query, ignoreCase = true) ||
                item.description.contains(query, ignoreCase = true)
        val matchesCategory = selectedCategory == "Semua" || item.category == selectedCategory
        matchesQuery && matchesCategory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("search_screen")
    ) {
        // Top app bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, 
                    contentDescription = "Kembali",
                    tint = OnSurfaceText
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cari Makanan",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurfaceText,
                letterSpacing = (-0.5).sp
            )
        }

        // Search Input
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.setQuery(it) },
            placeholder = { Text("Cari makanan, restoran, atau brand...", color = OnSurfaceVariantText) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Cari", tint = OnSurfaceVariantText) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setQuery("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Hapus", tint = OnSurfaceText)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("search_input"),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = OutlineVariant,
                focusedTextColor = OnSurfaceText,
                unfocusedTextColor = OnSurfaceText
            )
        )

        // Horizontal Categories List
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                val isSelected = selectedCategory == category
                Surface(
                    onClick = { viewModel.setCategory(category) },
                    shape = RoundedCornerShape(50),
                    color = if (isSelected) MaterialTheme.colorScheme.primary else ContainerLow,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else OnSurfaceVariantText,
                    modifier = Modifier.padding(vertical = 4.dp),
                    border = if (isSelected) null else BorderStroke(1.dp, OutlineVariant)
                ) {
                    Text(
                        text = category,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Results count
        Text(
            text = "Menampilkan ${filteredItems.size} hasil untuk \"${query.ifEmpty { "Semua" }}\"",
            fontSize = 13.sp,
            color = OnSurfaceVariantText,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Foods List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredItems) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, OutlineVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Box(modifier = Modifier.size(80.dp)) {
                            AsyncImage(
                                model = item.imageUrl,
                                contentDescription = item.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                            if (item.isPopular) {
                                Surface(
                                    color = Color(0xFFFEF3C7),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .align(Alignment.TopStart)
                                ) {
                                    Text(
                                        text = "POPULER",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFB45309),
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f),
                                    color = OnSurfaceText
                                )
                                Text(
                                    text = "${item.calories} kcal",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 14.sp
                                )
                            }
                            Text(
                                text = item.description,
                                fontSize = 12.sp,
                                color = OnSurfaceVariantText,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Rating",
                                        tint = Color(0xFFFBC02D),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${item.rating}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurfaceVariantText
                                    )
                                }

                                if (item.id == "nasi_padang") {
                                    Button(
                                        onClick = onNavigateToCustomization,
                                        shape = RoundedCornerShape(12.dp),
                                        contentPadding = ButtonDefaults.ContentPadding,
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text("Kustomisasi", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(14.dp))
                                    }
                                } else {
                                    OutlinedButton(
                                        onClick = {
                                            viewModel.logMeal(
                                                name = item.name,
                                                calories = item.calories,
                                                protein = item.protein,
                                                carbs = item.carbs,
                                                fat = item.fat,
                                                mealType = defaultMealType
                                            )
                                            onBack()
                                        },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text("Tambah Cepat", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomizationScreen(
    viewModel: NutriTrackViewModel,
    targetMealType: String,
    onLoggedAndBack: () -> Unit,
    onBack: () -> Unit
) {
    // Customization selections state
    val baseKcal = 350
    val baseProtein = 8
    val baseCarbs = 75
    val baseFat = 2

    val selectedAddons = remember { mutableStateMapOf<String, Boolean>() }

    // Dynamic calculations
    var currentCalories by remember { mutableStateOf(baseKcal) }
    var currentProtein by remember { mutableStateOf(baseProtein) }
    var currentCarbs by remember { mutableStateOf(baseCarbs) }
    var currentFat by remember { mutableStateOf(baseFat) }

    fun recalculate() {
        var kcal = baseKcal
        var p = baseProtein
        var c = baseCarbs
        var f = baseFat

        if (selectedAddons["rendang"] == true) {
            kcal += 300
            p += 22
            c += 5
            f += 16
        }
        if (selectedAddons["ikan-bakar"] == true) {
            kcal += 250
            p += 25
            c += 2
            f += 12
        }
        if (selectedAddons["ayam-pop"] == true) {
            kcal += 280
            p += 24
            c += 1
            f += 14
        }
        if (selectedAddons["sayur-nangka"] == true) {
            kcal += 50
            p += 1
            c += 8
            f += 3
        }

        currentCalories = kcal
        currentProtein = p
        currentCarbs = c
        currentFat = f
    }

    LaunchedEffect(selectedAddons.keys.toList()) {
        recalculate()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("customization_screen")
    ) {
        // App bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, 
                    contentDescription = "Kembali",
                    tint = OnSurfaceText
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Kustomisasi Paket",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurfaceText,
                letterSpacing = (-0.5).sp
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero banner Nasi Padang
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, OutlineVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().aspectRatio(1.7f)) {
                        AsyncImage(
                            model = URL_NASI_PADANG_BASE,
                            contentDescription = "Nasi Padang",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    androidx.compose.ui.graphics.Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f)),
                                        startY = 100f
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(20.dp)
                        ) {
                            Text(
                                text = "Nasi Padang",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Porsi Dasar: Nasi Putih + Sambal khas ($baseKcal kcal)",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            // Select Add-ons section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pilih Lauk Tambahan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceText
                    )
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFEF3C7), RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "TINGGI PROTEIN",
                            color = Color(0xFFB45309),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            // Rendang Item
            item {
                AddonItemCard(
                    title = "Rendang Sapi",
                    calories = 300,
                    imageUrl = URL_RENDANG,
                    isSelected = selectedAddons["rendang"] == true,
                    onCheckedChange = {
                        selectedAddons["rendang"] = it
                        recalculate()
                    }
                )
            }

            // Ikan Bakar Item
            item {
                AddonItemCard(
                    title = "Ikan Bakar",
                    calories = 250,
                    imageUrl = URL_IKAN_BAKAR,
                    isSelected = selectedAddons["ikan-bakar"] == true,
                    onCheckedChange = {
                        selectedAddons["ikan-bakar"] = it
                        recalculate()
                    }
                )
            }

            // Ayam Pop Item
            item {
                AddonItemCard(
                    title = "Ayam Pop",
                    calories = 280,
                    imageUrl = URL_AYAM_POP,
                    isSelected = selectedAddons["ayam-pop"] == true,
                    onCheckedChange = {
                        selectedAddons["ayam-pop"] = it
                        recalculate()
                    }
                )
            }

            // Sayur Nangka Item
            item {
                AddonItemCard(
                    title = "Sayur Nangka Gulai",
                    calories = 50,
                    imageUrl = URL_SAYUR_NANGKA,
                    isSelected = selectedAddons["sayur-nangka"] == true,
                    onCheckedChange = {
                        selectedAddons["sayur-nangka"] = it
                        recalculate()
                    }
                )
            }

            // Quick Breakdown summary
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = ContainerLow),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, OutlineVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "PREDIKSI MAKRONUTRISI",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceVariantText,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                Text("${currentProtein}g", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                                Text("Protein", fontSize = 12.sp, color = OnSurfaceVariantText, fontWeight = FontWeight.Medium)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                Text("${currentCarbs}g", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = ProgressOrange)
                                Text("Karbohidrat", fontSize = 12.sp, color = OnSurfaceVariantText, fontWeight = FontWeight.Medium)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                Text("${currentFat}g", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = ProgressGreen)
                                Text("Lemak", fontSize = 12.sp, color = OnSurfaceVariantText, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Action Counter Bar at Bottom
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, OutlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Estimasi Total Nutrisi", fontSize = 13.sp, color = OnSurfaceVariantText, fontWeight = FontWeight.Medium)
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "$currentCalories",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurfaceText
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("kcal", fontSize = 14.sp, color = OnSurfaceVariantText, modifier = Modifier.padding(bottom = 4.dp))
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        val percentage = (currentCalories.toFloat() / viewModel.targetCalories.toFloat()) * 100
                        LinearProgressIndicator(
                            progress = { (currentCalories.toFloat() / viewModel.targetCalories.toFloat()).coerceAtMost(1f) },
                            modifier = Modifier
                                .width(100.dp)
                                .height(6.dp)
                                .clip(CircleShape),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = OutlineVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${percentage.toInt()}% dari target harian",
                            fontSize = 11.sp,
                            color = OnSurfaceVariantText
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Create customized name listing addons
                        val addonsList = mutableListOf<String>()
                        if (selectedAddons["rendang"] == true) addonsList.add("Rendang")
                        if (selectedAddons["ikan-bakar"] == true) addonsList.add("Ikan Bakar")
                        if (selectedAddons["ayam-pop"] == true) addonsList.add("Ayam Pop")
                        if (selectedAddons["sayur-nangka"] == true) addonsList.add("Sayur Nangka")

                        val finalName = if (addonsList.isEmpty()) {
                            "Nasi Padang (Kustom)"
                        } else {
                            "Nasi Padang + " + addonsList.joinToString(" + ")
                        }

                        viewModel.logMeal(
                            name = finalName,
                            calories = currentCalories,
                            protein = currentProtein,
                            carbs = currentCarbs,
                            fat = currentFat,
                            mealType = targetMealType
                        )
                        onLoggedAndBack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_log_custom_meal"),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Log", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Catat Hidangan Kustom", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun AddonItemCard(
    title: String,
    calories: Int,
    imageUrl: String,
    isSelected: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isSelected) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else OutlineVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = OnSurfaceText)
                    Text("+$calories kcal", fontSize = 12.sp, color = OnSurfaceVariantText)
                }
            }

            Checkbox(
                checked = isSelected,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = Color.Gray
                )
            )
        }
    }
}

@Composable
fun ProfileScreen(viewModel: NutriTrackViewModel, onLogout: () -> Unit) {
    val totalCalories by viewModel.totalCalories.collectAsState()
    val healthInsight by viewModel.healthInsight.collectAsState()
    val isGeneratingInsight by viewModel.isGeneratingInsight.collectAsState()

    val currentUser by viewModel.currentUser.collectAsState()
    val userName = currentUser?.name ?: "Al Amin Abdilah"
    val targetCalories = currentUser?.targetCalories ?: 2500

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .testTag("profile_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App top header
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Profil Kesehatan",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceText,
                    letterSpacing = (-0.5).sp
                )
                IconButton(
                    onClick = { viewModel.refreshInsight() },
                    modifier = Modifier.background(ContainerLow, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh, 
                        contentDescription = "Muat Ulang Insight",
                        tint = OnSurfaceText
                    )
                }
            }
        }

        // Profile Avatar Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    AsyncImage(
                        model = URL_AVATAR,
                        contentDescription = "Avatar $userName",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Foto",
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = userName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceText
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Premium Status",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Premium Member",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
                Text(
                    text = "Target Gizi: ${java.text.NumberFormat.getIntegerInstance(java.util.Locale("id", "ID")).format(targetCalories)} kcal/hari",
                    fontSize = 13.sp,
                    color = OnSurfaceVariantText,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Health Snapshot Title
        item {
            Text(
                text = "Snapshot Kesehatan",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurfaceText
            )
        }

        // Health Cards row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Weight Card
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, OutlineVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Berat Badan", 
                            fontSize = 11.sp, 
                            fontWeight = FontWeight.Bold, 
                            color = OnSurfaceVariantText,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "78.5 kg",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "↓ -0.4 kg",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ProgressGreen
                        )
                    }
                }

                // BMI Card
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, OutlineVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "BMI", 
                            fontSize = 11.sp, 
                            fontWeight = FontWeight.Bold, 
                            color = OnSurfaceVariantText,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "23.4",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Rentang Normal",
                            fontSize = 12.sp,
                            color = OnSurfaceVariantText
                        )
                    }
                }
            }
        }

        // Weekly Avg Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, OutlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Rata-Rata Asupan Mingguan", 
                        fontSize = 11.sp, 
                        fontWeight = FontWeight.Bold, 
                        color = OnSurfaceVariantText,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "2.340 kcal/hari",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceText
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val progressVal = 2340f / 2500f
                    LinearProgressIndicator(
                        progress = { progressVal },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = ContainerLow
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "94% dari target kalori Anda tercapai",
                        fontSize = 11.sp,
                        color = OnSurfaceVariantText
                    )
                }
            }
        }

        // Gemini AI Insight Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Gemini Insight",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Wawasan Nutrisi Cerdas",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFDBEAFE), RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "GEMINI AI",
                                color = Color(0xFF1E40AF),
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (isGeneratingInsight) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 12.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Menganalisis asupan harian Anda...",
                                fontSize = 13.sp,
                                color = OnSurfaceVariantText
                            )
                        }
                    } else {
                        Text(
                            text = healthInsight,
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            color = OnSurfaceText
                        )
                    }
                }
            }
        }

        // Account Settings List
        item {
            Text(
                text = "Pengaturan Akun",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurfaceText,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, OutlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column {
                    SettingsListItem(
                        title = "Informasi Personal",
                        subtitle = "Kelola data pribadi dan privasi Anda",
                        icon = Icons.Default.Person
                    )
                    SettingsListItem(
                        title = "Simulasi Cek Pukul 20:00",
                        subtitle = "Uji Peringatan Defisit Kalori",
                        icon = Icons.Default.Info,
                        onClick = { viewModel.simulateDeficitCheck() }
                    )
                    SettingsListItem(
                        title = "Reset ke Data Awal",
                        subtitle = "Kembalikan data visual ke 1.200 kcal",
                        icon = Icons.Default.Refresh,
                        onClick = { viewModel.resetToMockData() }
                    )
                    SettingsListItem(
                        title = "Hapus Semua Log",
                        subtitle = "Kosongkan riwayat makanan Anda",
                        icon = Icons.Default.Delete,
                        onClick = { viewModel.clearAllMeals() }
                    )
                    SettingsListItem(
                        title = "Keluar / Log Out",
                        subtitle = "Keluar dari akun Anda dan bersihkan sesi",
                        icon = Icons.Default.ExitToApp,
                        onClick = {
                            viewModel.logout {
                                onLogout()
                            }
                        }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun SettingsListItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(ContainerLow, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title, 
                    fontWeight = FontWeight.SemiBold, 
                    fontSize = 15.sp,
                    color = OnSurfaceText
                )
                Text(
                    text = subtitle, 
                    fontSize = 12.sp, 
                    color = OnSurfaceVariantText
                )
            }
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Buka",
            tint = OnSurfaceVariantText,
            modifier = Modifier.size(18.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: NutriTrackViewModel,
    onLoginSuccess: () -> Unit
) {
    var isSignUpMode by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var targetCaloriesStr by remember { mutableStateOf("2500") }
    var keepMeLoggedIn by remember { mutableStateOf(true) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 400.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Top Branding
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Logo",
                    tint = PrimaryBlue,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = "NutriTrack",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryBlue,
                    letterSpacing = (-0.5).sp
                )
            }

            Text(
                text = "Precision nutrition management.",
                fontSize = 14.sp,
                color = OnSurfaceVariantText,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Login/Signup Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppSurface),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, OutlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = if (isSignUpMode) "Daftar Akun Baru" else "Welcome Back",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceText
                    )

                    Text(
                        text = if (isSignUpMode) "Masukkan data Anda untuk memulai pemantauan gizi presisi." else "Enter your credentials to access your dashboard.",
                        fontSize = 13.sp,
                        color = OnSurfaceVariantText,
                        lineHeight = 18.sp
                    )

                    if (errorMessage != null) {
                        Surface(
                            color = WarningRed.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, WarningRed.copy(alpha = 0.2f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = errorMessage ?: "",
                                color = WarningRed,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(12.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    if (successMessage != null) {
                        Surface(
                            color = ProgressGreen.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, ProgressGreen.copy(alpha = 0.2f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = successMessage ?: "",
                                color = ProgressGreen,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(12.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    if (isSignUpMode) {
                        // Name Field
                        Text(
                            text = "NAMA LENGKAP",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceVariantText,
                            letterSpacing = 0.5.sp
                        )
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it; errorMessage = null },
                            placeholder = { Text("John Doe", color = OnSurfaceVariantText.copy(alpha = 0.6f)) },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = OnSurfaceVariantText) },
                            modifier = Modifier.fillMaxWidth().testTag("signup_name_input"),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = OutlineVariant,
                                focusedContainerColor = AppBackground,
                                unfocusedContainerColor = AppBackground
                            ),
                            singleLine = true
                        )

                        // Target Calories Field
                        Text(
                            text = "TARGET KALORI HARIAN (KCAL)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceVariantText,
                            letterSpacing = 0.5.sp
                        )
                        OutlinedTextField(
                            value = targetCaloriesStr,
                            onValueChange = { targetCaloriesStr = it; errorMessage = null },
                            placeholder = { Text("2500", color = OnSurfaceVariantText.copy(alpha = 0.6f)) },
                            leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, tint = OnSurfaceVariantText) },
                            modifier = Modifier.fillMaxWidth().testTag("signup_target_input"),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = OutlineVariant,
                                focusedContainerColor = AppBackground,
                                unfocusedContainerColor = AppBackground
                            ),
                            singleLine = true
                        )
                    }

                    // Email Field
                    Text(
                        text = "EMAIL ADDRESS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceVariantText,
                        letterSpacing = 0.5.sp
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; errorMessage = null },
                        placeholder = { Text("name@company.com", color = OnSurfaceVariantText.copy(alpha = 0.6f)) },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = OnSurfaceVariantText) },
                        modifier = Modifier.fillMaxWidth().testTag("email_input"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = OutlineVariant,
                            focusedContainerColor = AppBackground,
                            unfocusedContainerColor = AppBackground
                        ),
                        singleLine = true
                    )

                    // Password Field
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PASSWORD",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceVariantText,
                            letterSpacing = 0.5.sp
                        )
                        if (!isSignUpMode) {
                            Text(
                                text = "Forgot Password?",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryBlue,
                                modifier = Modifier.clickable {
                                    errorMessage = "Fitur Lupa Kata Sandi disimulasikan: Silakan gunakan password pre-seed 'password123'."
                                }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; errorMessage = null },
                        placeholder = { Text("••••••••", color = OnSurfaceVariantText.copy(alpha = 0.6f)) },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = OnSurfaceVariantText) },
                        modifier = Modifier.fillMaxWidth().testTag("password_input"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = OutlineVariant,
                            focusedContainerColor = AppBackground,
                            unfocusedContainerColor = AppBackground
                        ),
                        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                        singleLine = true
                    )

                    // Keep me logged in checkbox (only on login)
                    if (!isSignUpMode) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().clickable { keepMeLoggedIn = !keepMeLoggedIn }
                        ) {
                            Checkbox(
                                checked = keepMeLoggedIn,
                                onCheckedChange = { keepMeLoggedIn = it },
                                colors = CheckboxDefaults.colors(checkedColor = PrimaryBlue)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Keep me logged in",
                                fontSize = 13.sp,
                                color = OnSurfaceVariantText
                            )
                        }
                    }

                    // Submit button
                    Button(
                        onClick = {
                            if (isSignUpMode) {
                                if (name.isBlank() || email.isBlank() || password.isBlank() || targetCaloriesStr.isBlank()) {
                                    errorMessage = "Semua field harus diisi."
                                    return@Button
                                }
                                val calories = targetCaloriesStr.toIntOrNull()
                                if (calories == null || calories <= 0) {
                                    errorMessage = "Target kalori harus berupa angka positif."
                                    return@Button
                                }
                                viewModel.register(name, email, password, calories, onSuccess = {
                                    successMessage = "Registrasi sukses! Silakan login."
                                    isSignUpMode = false
                                    errorMessage = null
                                }, onError = {
                                    errorMessage = it
                                })
                            } else {
                                if (email.isBlank() || password.isBlank()) {
                                    errorMessage = "Email dan password harus diisi."
                                    return@Button
                                }
                                viewModel.login(email, password, keepMeLoggedIn, onSuccess = {
                                    onLoginSuccess()
                                }, onError = {
                                    errorMessage = it
                                })
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp).testTag("btn_auth_submit"),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (isSignUpMode) "Daftar" else "Sign In",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.White
                        )
                    }
                }
            }

            // Social Divider & Buttons (Only on Login Mode for visual match)
            if (!isSignUpMode) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(OutlineVariant))
                    Text(
                        text = "OR CONTINUE WITH",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceVariantText,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        letterSpacing = 0.5.sp
                    )
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(OutlineVariant))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Google Mock Button
                    OutlinedButton(
                        onClick = { errorMessage = "Login Sosial disimulasikan. Silakan login menggunakan Akun Utama: amin13dpk@gmail.com / password123." },
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, OutlineVariant),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurfaceText)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("Google", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Apple Mock Button
                    OutlinedButton(
                        onClick = { errorMessage = "Login Sosial disimulasikan. Silakan login menggunakan Akun Utama: amin13dpk@gmail.com / password123." },
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, OutlineVariant),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurfaceText)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("Apple", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Footer Info
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isSignUpMode) "Sudah punya akun?" else "Don't have an account yet?",
                        fontSize = 13.sp,
                        color = OnSurfaceVariantText
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isSignUpMode) "Sign In" else "Sign Up",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue,
                        modifier = Modifier.clickable {
                            isSignUpMode = !isSignUpMode
                            errorMessage = null
                            successMessage = null
                        }
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Privacy Policy",
                        fontSize = 12.sp,
                        color = OnSurfaceVariantText,
                        modifier = Modifier.clickable { /* Simulate */ }
                    )
                    Text(
                        text = "Terms of Service",
                        fontSize = 12.sp,
                        color = OnSurfaceVariantText,
                        modifier = Modifier.clickable { /* Simulate */ }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
