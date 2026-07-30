package com.colorversekids.studio.app.core.data.local.database

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val id: Int = 1,
    val level: Int = 1,
    val currentXp: Int = 0,
    val requiredXp: Int = 100,
    val streakDays: Int = 1,
    val coins: Int = 100,
    val stars: Int = 15,
    val totalArtworksCompleted: Int = 0
)

@Entity(tableName = "saved_artworks")
data class SavedArtworkEntity(
    @PrimaryKey val id: String,
    val templateId: String,
    val title: String,
    val categoryId: String,
    val imagePath: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false,
    val completionPercentage: Float = 1.0f
)

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean = false,
    val currentProgress: Int = 0,
    val maxProgress: Int = 1,
    val rewardCoins: Int = 50,
    val rewardStars: Int = 5
)

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = 1")
    fun getUserProgress(): Flow<UserProgressEntity?>

    @Query("SELECT * FROM user_progress WHERE id = 1")
    suspend fun getUserProgressDirect(): UserProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: UserProgressEntity)
}

@Dao
interface SavedArtworkDao {
    @Query("SELECT * FROM saved_artworks ORDER BY timestamp DESC")
    fun getAllArtworks(): Flow<List<SavedArtworkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtwork(artwork: SavedArtworkEntity)

    @Query("DELETE FROM saved_artworks WHERE id = :id")
    suspend fun deleteArtwork(id: String)
}

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements")
    fun getAchievements(): Flow<List<AchievementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(achievements: List<AchievementEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAchievement(achievement: AchievementEntity)
}

@Database(
    entities = [UserProgressEntity::class, SavedArtworkEntity::class, AchievementEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProgressDao(): UserProgressDao
    abstract fun savedArtworkDao(): SavedArtworkDao
    abstract fun achievementDao(): AchievementDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "colorverse_kids_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
