package com.colorversekids.studio.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colorversekids.studio.app.core.data.repository.ColoringRepository
import com.colorversekids.studio.app.core.data.repository.ProgressRepository
import com.colorversekids.studio.app.core.model.Category
import com.colorversekids.studio.app.core.model.DailyChallenge
import com.colorversekids.studio.app.core.model.UserProgress
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class HomeUiState(
    val userProgress: UserProgress = UserProgress(),
    val dailyChallenge: DailyChallenge? = DailyChallenge(
        id = "daily_1",
        title = "Color the Cosmic Rocket to earn 50 Coins!",
        targetCategory = "space",
        rewardCoins = 50
    ),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false
)

class HomeViewModel(
    private val coloringRepository: ColoringRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val categories = coloringRepository.getCategories()
            progressRepository.userProgress.collect { progress ->
                _uiState.update {
                    it.copy(
                        userProgress = progress,
                        categories = categories
                    )
                }
            }
        }
    }
}
