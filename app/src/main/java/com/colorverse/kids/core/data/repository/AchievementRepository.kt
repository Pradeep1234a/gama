package com.colorverse.kids.core.data.repository

import com.colorverse.kids.core.data.local.database.AchievementDao
import com.colorverse.kids.core.data.local.database.AchievementEntity
import com.colorverse.kids.core.model.Achievement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AchievementRepository(private val achievementDao: AchievementDao) {

    val defaultAchievements = listOf(
        Achievement("ach_1", "First Splash", "Color your very first page!", "🎨", true, 1, 1, 50, 5),
        Achievement("ach_2", "Rainbow Explorer", "Use 10 different colors!", "🌈", true, 10, 10, 100, 10),
        Achievement("ach_3", "Animal Lover", "Color 5 animal pages!", "🦁", false, 3, 5, 150, 15),
        Achievement("ach_4", "Space Pioneer", "Color a cosmic rocket or planet!", "🚀", false, 0, 1, 100, 10),
        Achievement("ach_5", "Sticker Fanatic", "Place 20 stickers on drawings!", "⭐", false, 8, 20, 200, 20),
        Achievement("ach_6", "7-Day Streak Legend", "Play ColorVerse Kids for 7 days in a row!", "🔥", false, 3, 7, 500, 50)
    )

    val achievements: Flow<List<Achievement>> = achievementDao.getAchievements().map { entities ->
        if (entities.isEmpty()) {
            defaultAchievements
        } else {
            entities.map {
                Achievement(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    iconEmoji = it.iconEmoji,
                    isUnlocked = it.isUnlocked,
                    currentProgress = it.currentProgress,
                    maxProgress = it.maxProgress,
                    rewardCoins = it.rewardCoins,
                    rewardStars = it.rewardStars
                )
            }
        }
    }
}
