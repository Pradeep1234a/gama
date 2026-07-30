package com.colorversekids.studio.app.core.model

import androidx.compose.ui.graphics.Color

enum class Difficulty {
    EASY, MEDIUM, HARD
}

data class VectorRegion(
    val id: String,
    val name: String,
    val pathData: String,
    val defaultColor: Color = Color.White
)

data class ColoringPage(
    val id: String,
    val title: String,
    val categoryId: String,
    val difficulty: Difficulty,
    val iconEmoji: String,
    val regions: List<VectorRegion> = emptyList(),
    val isUnlocked: Boolean = true,
    val requiredStars: Int = 0
)
