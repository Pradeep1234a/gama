package com.colorverse.kids.feature.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colorverse.kids.core.data.local.datastore.UserPreferencesRepository
import com.colorverse.kids.core.model.ParentSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ParentUiState(
    val isGateUnlocked: Boolean = false,
    val gateQuestion: String = "7 × 8 = ?",
    val expectedAnswer: Int = 56,
    val settings: ParentSettings = ParentSettings(),
    val totalTimeSpentMinutes: Int = 45,
    val completedDrawingsCount: Int = 12
)

class ParentViewModel(
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ParentUiState())
    val uiState: StateFlow<ParentUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.parentSettingsFlow.collect { settings ->
                _uiState.update { it.copy(settings = settings) }
            }
        }
    }

    fun verifyGateAnswer(userAnswer: String): Boolean {
        val answerInt = userAnswer.toIntOrNull()
        if (answerInt == _uiState.value.expectedAnswer) {
            _uiState.update { it.copy(isGateUnlocked = true) }
            return true
        }
        return false
    }

    fun updateScreenTime(minutes: Int) {
        val updated = _uiState.value.settings.copy(screenTimeLimitMinutes = minutes)
        viewModelScope.launch {
            preferencesRepository.updateParentSettings(updated)
        }
    }

    fun toggleSound(enabled: Boolean) {
        val updated = _uiState.value.settings.copy(isSoundEffectsEnabled = enabled)
        viewModelScope.launch {
            preferencesRepository.updateParentSettings(updated)
        }
    }

    fun toggleVoice(enabled: Boolean) {
        val updated = _uiState.value.settings.copy(isVoiceGuidanceEnabled = enabled)
        viewModelScope.launch {
            preferencesRepository.updateParentSettings(updated)
        }
    }
}
