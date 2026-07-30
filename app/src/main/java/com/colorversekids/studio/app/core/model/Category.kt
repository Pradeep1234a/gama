package com.colorversekids.studio.app.core.model

import androidx.compose.ui.graphics.Color

data class Category(
    val id: String,
    val name: String,
    val description: String,
    val emoji: String,
    val color: Color,
    val itemCount: Int = 12,
    val isFeatured: Boolean = false
)
