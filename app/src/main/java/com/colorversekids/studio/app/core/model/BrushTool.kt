package com.colorversekids.studio.app.core.model

import androidx.compose.ui.graphics.Color

enum class BrushType {
    PENCIL,
    CRAYON,
    PAINT_BRUSH,
    MARKER,
    MAGIC_GLOW,
    ERASER,
    BUCKET_FILL
}

data class BrushTool(
    val type: BrushType,
    val name: String,
    val iconEmoji: String,
    val defaultWidth: Float = 12f,
    val defaultAlpha: Float = 1.0f
)
