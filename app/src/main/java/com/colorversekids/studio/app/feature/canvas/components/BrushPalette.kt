package com.colorversekids.studio.app.feature.canvas.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.colorversekids.studio.app.core.designsystem.ColorSwatches
import com.colorversekids.studio.app.core.model.BrushTool
import com.colorversekids.studio.app.core.model.BrushType

val AvailableBrushes = listOf(
    BrushTool(BrushType.PENCIL, "Pencil", "✏️", 8f),
    BrushTool(BrushType.CRAYON, "Crayon", "🖍️", 16f),
    BrushTool(BrushType.PAINT_BRUSH, "Paint", "🖌️", 18f),
    BrushTool(BrushType.MARKER, "Marker", "🖊️", 22f),
    BrushTool(BrushType.MAGIC_GLOW, "Glow", "✨", 16f),
    BrushTool(BrushType.BUCKET_FILL, "Fill", "🪣", 0f),
    BrushTool(BrushType.ERASER, "Eraser", "🧹", 28f)
)

@Composable
fun BrushPalette(
    selectedBrush: BrushType,
    onSelectBrush: (BrushType) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = modifier
    ) {
        items(AvailableBrushes) { brush ->
            val selected = brush.type == selectedBrush
            Surface(
                onClick = { onSelectBrush(brush.type) },
                shape = RoundedCornerShape(16.dp),
                color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                border = if (selected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(text = brush.iconEmoji, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = brush.name,
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ColorPickerBar(
    selectedColor: Color,
    onSelectColor: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = modifier
    ) {
        items(ColorSwatches) { color ->
            val selected = color == selectedColor
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = if (selected) 3.5.dp else 1.dp,
                        color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.4f),
                        shape = CircleShape
                    )
                    .clickable { onSelectColor(color) }
            )
        }
    }
}
