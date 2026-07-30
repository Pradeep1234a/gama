package com.colorverse.kids.core.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class PathPoint(
    val x: Float,
    val y: Float
)

data class CanvasStroke(
    val id: String = java.util.UUID.randomUUID().toString(),
    val points: List<PathPoint> = emptyList(),
    val color: Color,
    val width: Float,
    val alpha: Float,
    val brushType: BrushType
)

data class FilledRegionState(
    val regionId: String,
    val color: Color
)
