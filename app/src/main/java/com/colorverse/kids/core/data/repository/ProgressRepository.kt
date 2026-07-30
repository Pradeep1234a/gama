package com.colorverse.kids.core.data.repository

import com.colorverse.kids.core.data.local.database.UserProgressDao
import com.colorverse.kids.core.data.local.database.UserProgressEntity
import com.colorverse.kids.core.model.UserProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProgressRepository(private val userProgressDao: UserProgressDao) {

    val userProgress: Flow<UserProgress> = userProgressDao.getUserProgress().map { entity ->
        if (entity != null) {
            UserProgress(
                level = entity.level,
                currentXp = entity.currentXp,
                requiredXp = entity.requiredXp,
                streakDays = entity.streakDays,
                coins = entity.coins,
                stars = entity.stars,
                totalArtworksCompleted = entity.totalArtworksCompleted
            )
        } else {
            UserProgress()
        }
    }

    suspend fun addReward(xpGained: Int, coinsGained: Int, starsGained: Int) {
        val currentEntity = userProgressDao.getUserProgressDirect()
        val currentXp = (currentEntity?.currentXp ?: 0) + xpGained
        val currentCoins = (currentEntity?.coins ?: 100) + coinsGained
        val currentStars = (currentEntity?.stars ?: 15) + starsGained
        val newLevel = (currentEntity?.level ?: 1) + (currentXp / 100)

        val updated = UserProgressEntity(
            id = 1,
            level = newLevel,
            currentXp = currentXp % 100,
            requiredXp = newLevel * 100,
            streakDays = currentEntity?.streakDays ?: 1,
            coins = currentCoins,
            stars = currentStars,
            totalArtworksCompleted = (currentEntity?.totalArtworksCompleted ?: 0) + 1
        )
        userProgressDao.insertOrUpdate(updated)
    }
}
