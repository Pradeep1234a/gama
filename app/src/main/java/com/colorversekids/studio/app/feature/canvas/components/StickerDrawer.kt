package com.colorversekids.studio.app.feature.canvas.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.colorversekids.studio.app.core.model.Sticker

val SampleStickers = listOf(
    Sticker("st_1", "⭐", "Star"),
    Sticker("st_2", "🌈", "Rainbow"),
    Sticker("st_3", "💖", "Heart"),
    Sticker("st_4", "👑", "Crown"),
    Sticker("st_5", "🦄", "Unicorn"),
    Sticker("st_6", "🦋", "Butterfly"),
    Sticker("st_7", "🎈", "Balloon"),
    Sticker("st_8", "🚀", "Rocket")
)

@Composable
fun StickerDrawer(
    onSelectSticker: (Sticker) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "Tap to Add Sticker 🌟",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(SampleStickers) { sticker ->
                    Surface(
                        onClick = { onSelectSticker(sticker) },
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(4.dp)
                        ) {
                            Text(text = sticker.emoji, fontSize = 28.sp)
                        }
                    }
                }
            }
        }
    }
}
