package com.colorversekids.studio.app.feature.gamification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colorversekids.studio.app.core.data.repository.AchievementRepository
import com.colorversekids.studio.app.core.data.repository.ProgressRepository
import com.colorversekids.studio.app.core.model.Achievement
import com.colorversekids.studio.app.core.model.UserProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GamificationUiState(
    val userProgress: UserProgress = UserProgress(),
    val achievements: List<Achievement> = emptyList(),
    val showChestDialog: Boolean = false
)

class GamificationViewModel(
    private val progressRepository: ProgressRepository,
    private val achievementRepository: AchievementRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GamificationUiState())
    val uiState: StateFlow<GamificationUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            progressRepository.userProgress.collect { progress ->
                _uiState.update { it.copy(userProgress = progress) }
            }
        }
        viewModelScope.launch {
            achievementRepository.achievements.collect { list ->
                _uiState.update { it.copy(achievements = list) }
            }
        }
    }

    fun openTreasureChest() {
        viewModelScope.launch {
            progressRepository.addReward(xpGained = 100, coinsGained = 50, starsGained = 5)
            _uiState.update { it.copy(showChestDialog = true) }
        }
    }

    fun dismissChestDialog() {
        _uiState.update { it.copy(showChestDialog = false) }
    }
}
