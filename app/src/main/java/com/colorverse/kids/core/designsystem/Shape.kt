package com.colorverse.kids.core.designsystem

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val KidsShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

object KidsSpacing {
    val dp4: Dp = 4.dp
    val dp8: Dp = 8.dp
    val dp12: Dp = 12.dp
    val dp16: Dp = 16.dp
    val dp20: Dp = 20.dp
    val dp24: Dp = 24.dp
    val dp32: Dp = 32.dp
    val dp40: Dp = 40.dp
    val dp48: Dp = 48.dp
    val dp64: Dp = 64.dp

    val minTouchTarget: Dp = 56.dp
}
