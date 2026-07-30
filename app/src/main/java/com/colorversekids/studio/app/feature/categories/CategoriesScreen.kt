package com.colorversekids.studio.app.feature.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.colorversekids.studio.app.core.designsystem.components.KidsCard
import com.colorversekids.studio.app.core.designsystem.components.KidsTopBar
import com.colorversekids.studio.app.core.model.Category
import com.colorversekids.studio.app.core.model.ColoringPage

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel,
    onSelectPage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            KidsTopBar(title = "Categories Explorer 🗺️")
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
            // Horizontal Category Selector Tabs
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(uiState.categories) { category ->
                    val isSelected = category.id == uiState.selectedCategory?.id
                    Surface(
                        onClick = { viewModel.selectCategory(category) },
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) category.color else MaterialTheme.colorScheme.surfaceVariant,
                        tonalElevation = if (isSelected) 6.dp else 0.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Text(text = category.emoji, fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Category Info Banner
            uiState.selectedCategory?.let { category ->
                KidsCard(
                    containerColor = category.color.copy(alpha = 0.15f),
                    borderColor = category.color,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = category.emoji, fontSize = 44.sp)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = category.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Coloring Pages Grid
            Text(
                text = "Coloring Pages",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.coloringPages) { page ->
                    PageGridItem(
                        page = page,
                        onClick = { onSelectPage(page.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun PageGridItem(
    page: ColoringPage,
    onClick: () -> Unit
) {
    KidsCard(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.surface,
        elevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Text(text = page.iconEmoji, fontSize = 44.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = page.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = when (page.difficulty) {
                    com.colorversekids.studio.app.core.model.Difficulty.EASY -> Color(0xFFE8F5E9)
                    com.colorversekids.studio.app.core.model.Difficulty.MEDIUM -> Color(0xFFFFF8E1)
                    com.colorversekids.studio.app.core.model.Difficulty.HARD -> Color(0xFFFFEBEE)
                },
                modifier = Modifier.padding(top = 6.dp)
            ) {
                Text(
                    text = page.difficulty.name,
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 10.sp),
                    color = when (page.difficulty) {
                        com.colorversekids.studio.app.core.model.Difficulty.EASY -> Color(0xFF2E7D32)
                        com.colorversekids.studio.app.core.model.Difficulty.MEDIUM -> Color(0xFFF57F17)
                        com.colorversekids.studio.app.core.model.Difficulty.HARD -> Color(0xFFC62828)
                    },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}
