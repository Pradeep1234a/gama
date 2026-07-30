package com.colorversekids.studio.app.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
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
import com.colorversekids.studio.app.core.designsystem.*
import com.colorversekids.studio.app.core.designsystem.components.*
import com.colorversekids.studio.app.core.model.Category

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onCategoryClick: (String) -> Unit,
    onStartColoring: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            KidsTopBar(
                title = "ColorVerse Kids",
                userProgress = uiState.userProgress
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Child Welcome Banner
            WelcomeBanner(level = uiState.userProgress.level, xp = uiState.userProgress.currentXp)

            // Daily Challenge Hero Card
            uiState.dailyChallenge?.let { challenge ->
                DailyChallengeCard(
                    title = challenge.title,
                    rewardCoins = challenge.rewardCoins,
                    onStart = { onStartColoring("space_1") }
                )
            }

            // Category Launcher Section
            Text(
                text = "Explore Categories 🎨",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(uiState.categories) { category ->
                    CategoryCard(
                        category = category,
                        onClick = { onCategoryClick(category.id) }
                    )
                }
            }

            // Quick Play Featured Artworks
            Text(
                text = "Quick Play 🚀",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                QuickPlayItem(
                    emoji = "🚀",
                    title = "Cosmic Rocket",
                    category = "Space",
                    bgColor = SkyBlue,
                    modifier = Modifier.weight(1f),
                    onClick = { onStartColoring("space_1") }
                )
                QuickPlayItem(
                    emoji = "🦁",
                    title = "King Lion",
                    category = "Animals",
                    bgColor = SunshineGold,
                    modifier = Modifier.weight(1f),
                    onClick = { onStartColoring("animals_1") }
                )
            }
        }
    }
}

@Composable
private fun WelcomeBanner(level: Int, xp: Int) {
    KidsCard(
        containerColor = ElectricCoral.copy(alpha = 0.15f),
        borderColor = ElectricCoral,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(ElectricCoral)
            ) {
                Text(text = "🎨", fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Welcome, Artist! ✨",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                KidsProgressBar(
                    progress = xp / 100f,
                    levelText = "Level $level Progress"
                )
            }
        }
    }
}

@Composable
private fun DailyChallengeCard(
    title: String,
    rewardCoins: Int,
    onStart: () -> Unit
) {
    KidsCard(
        containerColor = SunshineGold.copy(alpha = 0.25f),
        borderColor = SunshineGold,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🔥 Daily Quest", style = MaterialTheme.typography.labelLarge.copy(color = ElectricCoral))
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SunshineGold
                ) {
                    Text(
                        text = "+$rewardCoins 🪙",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(12.dp))

            KidsPrimaryButton(
                onClick = onStart,
                containerColor = ElectricCoral,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Start")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Start Quest!", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
private fun CategoryCard(
    category: Category,
    onClick: () -> Unit
) {
    KidsCard(
        onClick = onClick,
        containerColor = category.color.copy(alpha = 0.15f),
        borderColor = category.color,
        modifier = Modifier.width(130.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = category.emoji, fontSize = 40.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${category.itemCount} Pages",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun QuickPlayItem(
    emoji: String,
    title: String,
    category: String,
    bgColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    KidsCard(
        onClick = onClick,
        containerColor = bgColor.copy(alpha = 0.2f),
        borderColor = bgColor,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = emoji, fontSize = 32.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
