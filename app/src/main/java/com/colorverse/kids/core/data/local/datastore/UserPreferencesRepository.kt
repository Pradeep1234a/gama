package com.colorverse.kids.core.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.colorverse.kids.core.model.ParentSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "parent_preferences")

class UserPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val PARENT_PIN = stringPreferencesKey("parent_pin")
        val SCREEN_TIME_LIMIT = intPreferencesKey("screen_time_limit")
        val SOUND_EFFECTS = booleanPreferencesKey("sound_effects")
        val VOICE_GUIDANCE = booleanPreferencesKey("voice_guidance")
        val HIGH_CONTRAST = booleanPreferencesKey("high_contrast")
    }

    val parentSettingsFlow: Flow<ParentSettings> = context.dataStore.data.map { preferences ->
        ParentSettings(
            parentPin = preferences[PreferencesKeys.PARENT_PIN] ?: "8888",
            screenTimeLimitMinutes = preferences[PreferencesKeys.SCREEN_TIME_LIMIT] ?: 30,
            isSoundEffectsEnabled = preferences[PreferencesKeys.SOUND_EFFECTS] ?: true,
            isVoiceGuidanceEnabled = preferences[PreferencesKeys.VOICE_GUIDANCE] ?: true,
            isHighContrastEnabled = preferences[PreferencesKeys.HIGH_CONTRAST] ?: false
        )
    }

    suspend fun updateParentSettings(settings: ParentSettings) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PARENT_PIN] = settings.parentPin
            preferences[PreferencesKeys.SCREEN_TIME_LIMIT] = settings.screenTimeLimitMinutes
            preferences[PreferencesKeys.SOUND_EFFECTS] = settings.isSoundEffectsEnabled
            preferences[PreferencesKeys.VOICE_GUIDANCE] = settings.isVoiceGuidanceEnabled
            preferences[PreferencesKeys.HIGH_CONTRAST] = settings.isHighContrastEnabled
        }
    }
}
