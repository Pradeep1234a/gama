package com.colorversekids.studio.app.feature.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colorversekids.studio.app.core.data.repository.ColoringRepository
import com.colorversekids.studio.app.core.model.Category
import com.colorversekids.studio.app.core.model.ColoringPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CategoriesUiState(
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val coloringPages: List<ColoringPage> = emptyList()
)

class CategoriesViewModel(
    private val coloringRepository: ColoringRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        val categories = coloringRepository.getCategories()
        val firstCategory = categories.firstOrNull()
        val pages = firstCategory?.let { coloringRepository.getColoringPagesForCategory(it.id) } ?: emptyList()

        _uiState.update {
            it.copy(
                categories = categories,
                selectedCategory = firstCategory,
                coloringPages = pages
            )
        }
    }

    fun selectCategory(category: Category) {
        val pages = coloringRepository.getColoringPagesForCategory(category.id)
        _uiState.update {
            it.copy(
                selectedCategory = category,
                coloringPages = pages
            )
        }
    }
}
