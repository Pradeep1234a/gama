package com.colorversekids.studio.app.feature.gamification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.colorversekids.studio.app.core.designsystem.*
import com.colorversekids.studio.app.core.designsystem.components.*
import com.colorversekids.studio.app.core.model.Achievement

@Composable
fun AchievementsScreen(
    viewModel: GamificationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            KidsTopBar(
                title = "Badges & Rewards 🏆",
                userProgress = uiState.userProgress
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Treasure Chest Hero Banner
            KidsCard(
                containerColor = SunshineGold.copy(alpha = 0.25f),
                borderColor = SunshineGold,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "🎁", fontSize = 48.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Surprise Chest!",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black)
                        )
                        Text(
                            text = "Tap to unlock surprise rewards!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    KidsPrimaryButton(
                        onClick = { viewModel.openTreasureChest() },
                        containerColor = ElectricCoral,
                        modifier = Modifier.height(44.dp)
                    ) {
                        Text(text = "Open!", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            Text(
                text = "Unlockable Badges",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.achievements) { achievement ->
                    AchievementRow(achievement = achievement)
                }
            }

            if (uiState.showChestDialog) {
                TreasureChestDialog(onDismiss = { viewModel.dismissChestDialog() })
            }
        }
    }
}

@Composable
private fun AchievementRow(achievement: Achievement) {
    KidsCard(
        containerColor = if (achievement.isUnlocked) MintMeadow.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface,
        borderColor = if (achievement.isUnlocked) MintMeadow else MaterialTheme.colorScheme.outlineVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(if (achievement.isUnlocked) SunshineGold.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(text = achievement.iconEmoji, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = achievement.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                KidsProgressBar(
                    progress = achievement.currentProgress.toFloat() / achievement.maxProgress.coerceAtLeast(1),
                    levelText = "${achievement.currentProgress}/${achievement.maxProgress}"
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            if (!achievement.isUnlocked) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MintMeadow
                ) {
                    Text(
                        text = "Unlocked!",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 10.sp),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TreasureChestDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(text = "🎁✨", fontSize = 56.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "CHEST OPENED!",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    color = SunshineGold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "You earned +100 XP, +50 Coins, and +5 Stars!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                KidsPrimaryButton(
                    onClick = onDismiss,
                    containerColor = SunshineGold,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Awesome!", style = MaterialTheme.typography.labelLarge, color = Color.Black)
                }
            }
        }
    }
}
