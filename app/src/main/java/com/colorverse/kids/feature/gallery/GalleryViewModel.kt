package com.colorverse.kids.feature.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colorverse.kids.core.data.local.database.SavedArtworkEntity
import com.colorverse.kids.core.data.repository.ColoringRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GalleryUiState(
    val artworks: List<SavedArtworkEntity> = listOf(
        SavedArtworkEntity("1", "space_1", "Cosmic Rocket", "space", "", isFavorite = true),
        SavedArtworkEntity("2", "animals_1", "Friendly Lion", "animals", "", isFavorite = false),
        SavedArtworkEntity("3", "fruits_1", "Juicy Apple", "fruits", "", isFavorite = true)
    ),
    val isLoading: Boolean = false
)

class GalleryViewModel(
    private val coloringRepository: ColoringRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GalleryUiState())
    val uiState: StateFlow<GalleryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            coloringRepository.savedArtworks.collect { list ->
                if (list.isNotEmpty()) {
                    _uiState.update { it.copy(artworks = list) }
                }
            }
        }
    }

    fun deleteArtwork(id: String) {
        viewModelScope.launch {
            coloringRepository.deleteArtwork(id)
            _uiState.update { state ->
                state.copy(artworks = state.artworks.filter { it.id != id })
            }
        }
    }
}
