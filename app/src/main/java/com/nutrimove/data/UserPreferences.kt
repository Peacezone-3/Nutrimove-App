package com.nutrimove.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore delegate on Context
val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserKeys {
    val NAME = stringPreferencesKey("name")
    val AGE = intPreferencesKey("age")
    val GOAL = stringPreferencesKey("goal")
    val HEIGHT = intPreferencesKey("height")
    val WEIGHT = intPreferencesKey("weight")
    val ACTIVITY = stringPreferencesKey("activity")
    val HAS_COMPLETED_ONBOARDING = booleanPreferencesKey("has_completed_onboarding")
    val TRAINING_DAYS = intPreferencesKey("training_days")
}

class UserPreferences(private val context: Context) {

    // --- salvar individualmente uma string ---
    suspend fun saveString(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    // --- salvar todos os dados do usuário de uma vez ---
    suspend fun saveUserData(
        name: String, age: Int, goal: String,
        height: Int, weight: Int, activity: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[UserKeys.NAME] = name
            prefs[UserKeys.AGE] = age
            prefs[UserKeys.GOAL] = goal
            prefs[UserKeys.HEIGHT] = height
            prefs[UserKeys.WEIGHT] = weight
            prefs[UserKeys.ACTIVITY] = activity
        }
    }

    // --- fluxo de dados do usuário ---
    val userFlow: Flow<Map<Preferences.Key<*>, Any>> =
        context.dataStore.data.map { it.asMap() }

    // --- flag de onboarding ---
    val onboardingCompletedFlow: Flow<Boolean> =
        context.dataStore.data.map { it[UserKeys.HAS_COMPLETED_ONBOARDING] ?: false }

    suspend fun setOnboardingCompleted() {
        context.dataStore.edit { prefs ->
            prefs[UserKeys.HAS_COMPLETED_ONBOARDING] = true
        }
    }

    // ✅ --- reset do onboarding ---
    suspend fun resetOnboarding() {
        context.dataStore.edit { prefs ->
            prefs[UserKeys.HAS_COMPLETED_ONBOARDING] = false
        }
    }

    // --- dias de treino ---
    val trainingDaysFlow: Flow<Int> =
        context.dataStore.data.map { it[UserKeys.TRAINING_DAYS] ?: 3 }

    suspend fun saveTrainingDays(days: Int) {
        context.dataStore.edit { prefs ->
            prefs[UserKeys.TRAINING_DAYS] = days
        }
    }
}
