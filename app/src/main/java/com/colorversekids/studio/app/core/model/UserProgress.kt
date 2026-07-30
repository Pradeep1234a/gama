package com.colorversekids.studio.app.core.model

data class UserProgress(
    val level: Int = 1,
    val currentXp: Int = 0,
    val requiredXp: Int = 100,
    val streakDays: Int = 1,
    val coins: Int = 100,
    val stars: Int = 15,
    val totalArtworksCompleted: Int = 0
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean = false,
    val currentProgress: Int = 0,
    val maxProgress: Int = 1,
    val rewardCoins: Int = 50,
    val rewardStars: Int = 5
)

data class DailyChallenge(
    val id: String,
    val title: String,
    val targetCategory: String,
    val targetCount: Int = 1,
    val currentCount: Int = 0,
    val isCompleted: Boolean = false,
    val rewardCoins: Int = 50
)

data class ParentSettings(
    val parentPin: String = "8888",
    val screenTimeLimitMinutes: Int = 30,
    val isSoundEffectsEnabled: Boolean = true,
    val isVoiceGuidanceEnabled: Boolean = true,
    val isHighContrastEnabled: Boolean = false
)
