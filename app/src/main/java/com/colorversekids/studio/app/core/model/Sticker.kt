package com.colorversekids.studio.app.core.model

data class Sticker(
    val id: String,
    val emoji: String,
    val name: String,
    val isUnlocked: Boolean = true,
    val requiredCoins: Int = 0
)

data class PlacedSticker(
    val id: String = java.util.UUID.randomUUID().toString(),
    val stickerId: String,
    val emoji: String,
    val x: Float,
    val y: Float,
    val scale: Float = 1.0f,
    val rotation: Float = 0f
)
