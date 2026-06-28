package com.example.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val name: String,
    val targetCalories: Int,
    val password: String
)

@Entity(tableName = "food_items")
data class FoodItem(
    @PrimaryKey val id: String,
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

@Entity(tableName = "daily_logs")
data class MealLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val mealType: String, // "Sarapan", "Makan Siang", "Makan Malam"
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getAnyUser(): User?
}

@Dao
interface FoodItemDao {
    @Query("SELECT * FROM food_items")
    fun getAllFoodItems(): Flow<List<FoodItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodItems(foods: List<FoodItem>)

    @Query("SELECT * FROM food_items WHERE name LIKE :query OR category LIKE :query")
    fun searchFoodItems(query: String): Flow<List<FoodItem>>

    @Query("SELECT COUNT(*) FROM food_items")
    suspend fun getCount(): Int
}

@Dao
interface MealDao {
    @Query("SELECT * FROM daily_logs ORDER BY timestamp DESC")
    fun getAllMeals(): Flow<List<MealLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealLog)

    @Query("DELETE FROM daily_logs WHERE id = :id")
    suspend fun deleteMealById(id: Int)

    @Query("DELETE FROM daily_logs")
    suspend fun clearAllMeals()
}

@Database(entities = [MealLog::class, User::class, FoodItem::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
    abstract fun userDao(): UserDao
    abstract fun foodItemDao(): FoodItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nutritrack_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class UserRepository(private val userDao: UserDao) {
    suspend fun getUserByEmail(email: String): User? = userDao.getUserByEmail(email)
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun getAnyUser(): User? = userDao.getAnyUser()
}

class FoodItemRepository(private val foodItemDao: FoodItemDao) {
    val allFoodItems: Flow<List<FoodItem>> = foodItemDao.getAllFoodItems()
    suspend fun insertFoodItems(foods: List<FoodItem>) = foodItemDao.insertFoodItems(foods)
    suspend fun getCount(): Int = foodItemDao.getCount()
    fun searchFoodItems(query: String): Flow<List<FoodItem>> = foodItemDao.searchFoodItems("%$query%")
}

class MealRepository(private val mealDao: MealDao) {
    val allMeals: Flow<List<MealLog>> = mealDao.getAllMeals()

    suspend fun insert(meal: MealLog) {
        mealDao.insertMeal(meal)
    }

    suspend fun deleteById(id: Int) {
        mealDao.deleteMealById(id)
    }

    suspend fun clearAll() {
        mealDao.clearAllMeals()
    }
}
