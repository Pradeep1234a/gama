package com.colorverse.kids.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.colorverse.kids.core.designsystem.SunshineGold

@Composable
fun KidsRewardModal(
    earnedXp: Int,
    earnedCoins: Int,
    earnedStars: Int,
    onDismiss: () -> Unit,
    onContinue: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 12.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "🎉 MASTERPIECE COMPLETE! 🎉",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    textAlign = TextAlign.Center,
                    color = SunshineGold
                )

                Text(
                    text = "🌟 Fantastic Coloring! 🌟",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RewardItem(emoji = "⚡", label = "+$earnedXp XP")
                    RewardItem(emoji = "🪙", label = "+$earnedCoins Coins")
                    RewardItem(emoji = "⭐", label = "+$earnedStars Stars")
                }

                KidsPrimaryButton(
                    onClick = onContinue,
                    containerColor = SunshineGold,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Claim Rewards!",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp),
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun RewardItem(emoji: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 28.sp)
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
