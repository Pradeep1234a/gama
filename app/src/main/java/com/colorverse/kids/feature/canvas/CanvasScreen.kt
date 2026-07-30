package com.colorverse.kids.feature.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.colorverse.kids.core.designsystem.ColorSwatches
import com.colorverse.kids.core.designsystem.ElectricCoral
import com.colorverse.kids.core.designsystem.SunshineGold
import com.colorverse.kids.core.designsystem.components.KidsPrimaryButton
import com.colorverse.kids.core.designsystem.components.KidsRewardModal
import com.colorverse.kids.core.designsystem.components.KidsTopBar
import com.colorverse.kids.core.model.BrushType
import com.colorverse.kids.core.model.CanvasStroke
import com.colorverse.kids.core.model.PathPoint
import com.colorverse.kids.feature.canvas.components.BrushPalette
import com.colorverse.kids.feature.canvas.components.ColorPickerBar
import com.colorverse.kids.feature.canvas.components.StickerDrawer

@Composable
fun CanvasScreen(
    viewModel: CanvasViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var currentPoints by remember { mutableStateOf(listOf<PathPoint>()) }
    var showStickers by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            KidsTopBar(
                title = uiState.pageTitle,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (showStickers) {
                    StickerDrawer(
                        onSelectSticker = { sticker ->
                            viewModel.addSticker(sticker)
                            showStickers = false
                        }
                    )
                }

                // Color Picker Row
                ColorPickerBar(
                    selectedColor = uiState.selectedColor,
                    onSelectColor = { viewModel.selectColor(it) }
                )

                // Brush Palette Row
                BrushPalette(
                    selectedBrush = uiState.selectedBrush,
                    onSelectBrush = { viewModel.selectBrush(it) }
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF9F9FB))
        ) {
            // Interactive Drawing Canvas
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(uiState.selectedBrush, uiState.selectedColor, uiState.brushWidth) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                currentPoints = listOf(PathPoint(offset.x, offset.y))
                            },
                            onDrag = { change, _ ->
                                currentPoints = currentPoints + PathPoint(change.position.x, change.position.y)
                            },
                            onDragEnd = {
                                if (currentPoints.isNotEmpty()) {
                                    viewModel.addStroke(
                                        CanvasStroke(
                                            points = currentPoints,
                                            color = if (uiState.selectedBrush == BrushType.ERASER) Color(0xFFF9F9FB) else uiState.selectedColor,
                                            width = uiState.brushWidth,
                                            alpha = 1.0f,
                                            brushType = uiState.selectedBrush
                                        )
                                    )
                                    currentPoints = emptyList()
                                }
                            }
                        )
                    }
            ) {
                // Draw Completed Strokes
                uiState.strokes.forEach { stroke ->
                    if (stroke.points.size > 1) {
                        val path = Path().apply {
                            moveTo(stroke.points.first().x, stroke.points.first().y)
                            for (i in 1 until stroke.points.size) {
                                lineTo(stroke.points[i].x, stroke.points[i].y)
                            }
                        }
                        drawPath(
                            path = path,
                            color = stroke.color,
                            style = Stroke(
                                width = stroke.width,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }

                // Draw Current Active Stroke
                if (currentPoints.size > 1) {
                    val currentPath = Path().apply {
                        moveTo(currentPoints.first().x, currentPoints.first().y)
                        for (i in 1 until currentPoints.size) {
                            lineTo(currentPoints[i].x, currentPoints[i].y)
                        }
                    }
                    drawPath(
                        path = currentPath,
                        color = if (uiState.selectedBrush == BrushType.ERASER) Color(0xFFF9F9FB) else uiState.selectedColor,
                        style = Stroke(
                            width = uiState.brushWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }

            // Placed Stickers Overlay
            uiState.placedStickers.forEach { placed ->
                Text(
                    text = placed.emoji,
                    fontSize = 44.sp,
                    modifier = Modifier.offset(x = placed.x.dp, y = placed.y.dp)
                )
            }

            // Floating Controls Toolbar (Undo, Redo, Clear, Stickers, Done)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            ) {
                IconButton(
                    onClick = { viewModel.undo() },
                    enabled = uiState.canUndo,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Icon(Icons.Default.Undo, contentDescription = "Undo")
                }

                IconButton(
                    onClick = { viewModel.redo() },
                    enabled = uiState.canRedo,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Icon(Icons.Default.Redo, contentDescription = "Redo")
                }

                IconButton(
                    onClick = { showStickers = !showStickers },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SunshineGold)
                ) {
                    Text(text = "⭐", fontSize = 18.sp)
                }

                IconButton(
                    onClick = { viewModel.clearCanvas() },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Clear")
                }

                KidsPrimaryButton(
                    onClick = { viewModel.completeArtwork() },
                    containerColor = ElectricCoral,
                    modifier = Modifier.height(44.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Finish")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Done!", style = MaterialTheme.typography.labelLarge)
                }
            }

            // Celebration Modal
            if (uiState.showRewardModal) {
                KidsRewardModal(
                    earnedXp = 50,
                    earnedCoins = 20,
                    earnedStars = 3,
                    onDismiss = { viewModel.dismissRewardModal() },
                    onContinue = {
                        viewModel.dismissRewardModal()
                        onBackClick()
                    }
                )
            }
        }
    }
}
