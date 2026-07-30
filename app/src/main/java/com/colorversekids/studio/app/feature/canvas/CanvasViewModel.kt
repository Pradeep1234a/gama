package com.colorversekids.studio.app.feature.canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colorversekids.studio.app.core.data.repository.ColoringRepository
import com.colorversekids.studio.app.core.data.repository.ProgressRepository
import com.colorversekids.studio.app.core.designsystem.ColorSwatches
import com.colorversekids.studio.app.core.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CanvasUiState(
    val pageId: String = "",
    val pageTitle: String = "Coloring Page",
    val strokes: List<CanvasStroke> = emptyList(),
    val filledRegions: Map<String, Color> = emptyMap(),
    val placedStickers: List<PlacedSticker> = emptyList(),
    val selectedColor: Color = ColorSwatches[0],
    val selectedBrush: BrushType = BrushType.PENCIL,
    val brushWidth: Float = 14f,
    val canUndo: Boolean = false,
    val canRedo: Boolean = false,
    val isCompleted: Boolean = false,
    val showRewardModal: Boolean = false,
    val voiceHint: String = "Tap or drag on the canvas to start coloring!"
)

class CanvasViewModel(
    private val coloringRepository: ColoringRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CanvasUiState())
    val uiState: StateFlow<CanvasUiState> = _uiState.asStateFlow()

    private val undoStack = mutableListOf<List<CanvasStroke>>()
    private val redoStack = mutableListOf<List<CanvasStroke>>()

    fun loadPage(pageId: String) {
        _uiState.update {
            it.copy(
                pageId = pageId,
                pageTitle = pageId.replace("_", " ").uppercase()
            )
        }
    }

    fun selectColor(color: Color) {
        _uiState.update { it.copy(selectedColor = color) }
    }

    fun selectBrush(brushType: BrushType) {
        _uiState.update {
            it.copy(
                selectedBrush = brushType,
                brushWidth = when (brushType) {
                    BrushType.PENCIL -> 8f
                    BrushType.MARKER -> 22f
                    BrushType.PAINT_BRUSH -> 18f
                    BrushType.MAGIC_GLOW -> 16f
                    BrushType.ERASER -> 28f
                    else -> 14f
                }
            )
        }
    }

    fun updateBrushWidth(width: Float) {
        _uiState.update { it.copy(brushWidth = width) }
    }

    fun addStroke(stroke: CanvasStroke) {
        saveStateForUndo()
        val updated = _uiState.value.strokes + stroke
        _uiState.update {
            it.copy(
                strokes = updated,
                canUndo = true,
                canRedo = false
            )
        }
        redoStack.clear()
    }

    fun fillRegion(regionId: String, color: Color) {
        val updated = _uiState.value.filledRegions.toMutableMap().apply {
            put(regionId, color)
        }
        _uiState.update { it.copy(filledRegions = updated) }
    }

    fun addSticker(sticker: Sticker) {
        val newSticker = PlacedSticker(
            stickerId = sticker.id,
            emoji = sticker.emoji,
            x = 200f,
            y = 200f
        )
        _uiState.update { it.copy(placedStickers = it.placedStickers + newSticker) }
    }

    fun undo() {
        if (undoStack.isNotEmpty()) {
            val previous = undoStack.removeAt(undoStack.size - 1)
            redoStack.add(_uiState.value.strokes)
            _uiState.update {
                it.copy(
                    strokes = previous,
                    canUndo = undoStack.isNotEmpty(),
                    canRedo = true
                )
            }
        }
    }

    fun redo() {
        if (redoStack.isNotEmpty()) {
            val next = redoStack.removeAt(redoStack.size - 1)
            undoStack.add(_uiState.value.strokes)
            _uiState.update {
                it.copy(
                    strokes = next,
                    canUndo = true,
                    canRedo = redoStack.isNotEmpty()
                )
            }
        }
    }

    fun clearCanvas() {
        saveStateForUndo()
        _uiState.update {
            it.copy(
                strokes = emptyList(),
                filledRegions = emptyMap(),
                placedStickers = emptyList(),
                canUndo = true
            )
        }
    }

    fun completeArtwork() {
        viewModelScope.launch {
            progressRepository.addReward(xpGained = 50, coinsGained = 20, starsGained = 3)
            _uiState.update { it.copy(isCompleted = true, showRewardModal = true) }
        }
    }

    fun dismissRewardModal() {
        _uiState.update { it.copy(showRewardModal = false) }
    }

    private fun saveStateForUndo() {
        undoStack.add(_uiState.value.strokes)
        if (undoStack.size > 30) {
            undoStack.removeAt(0)
        }
    }
}
