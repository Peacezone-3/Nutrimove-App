// app/src/main/java/com/nutrimove/data/UserPreferences.kt
package com.nutrimove.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

// DataStore delegate on Context
val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserKeys {
    // --- existing profile data ---
    val NAME                     = stringPreferencesKey("name")
    val AGE                      = intPreferencesKey("age")
    val GOAL                     = stringPreferencesKey("goal")
    val HEIGHT                   = intPreferencesKey("height")
    val WEIGHT                   = intPreferencesKey("weight")
    val ACTIVITY                 = stringPreferencesKey("activity")

    // --- onboarding flag ---
    val HAS_COMPLETED_ONBOARDING = booleanPreferencesKey("has_completed_onboarding")

    // --- training days ---
    val TRAINING_DAYS            = intPreferencesKey("training_days")

    // --- weight‐tracker keys (new) ---
    val GOAL_WEIGHT              = floatPreferencesKey("goal_weight")
    val WEIGHT_ENTRIES_JSON      = stringPreferencesKey("weight_entries_json")
}

class UserPreferences(private val context: Context) {
    private val gson = Gson()

    // --- existing profile data ---
    suspend fun saveUserData(
        name: String,
        age: Int,
        goal: String,
        height: Int,
        weight: Int,
        activity: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[UserKeys.NAME]     = name
            prefs[UserKeys.AGE]      = age
            prefs[UserKeys.GOAL]     = goal
            prefs[UserKeys.HEIGHT]   = height
            prefs[UserKeys.WEIGHT]   = weight
            prefs[UserKeys.ACTIVITY] = activity
        }
    }
    val userFlow: Flow<Map<Preferences.Key<*>, Any>> =
        context.dataStore.data.map { it.asMap() }

    // --- onboarding flag ---
    val onboardingCompletedFlow: Flow<Boolean> =
        context.dataStore.data.map { it[UserKeys.HAS_COMPLETED_ONBOARDING] ?: false }
    suspend fun setOnboardingCompleted() {
        context.dataStore.edit { prefs ->
            prefs[UserKeys.HAS_COMPLETED_ONBOARDING] = true
        }
    }

    // --- training days ---
    val trainingDaysFlow: Flow<Int> =
        context.dataStore.data.map { it[UserKeys.TRAINING_DAYS] ?: 3 }
    suspend fun saveTrainingDays(days: Int) {
        context.dataStore.edit { prefs ->
            prefs[UserKeys.TRAINING_DAYS] = days
        }
    }

    // --- weight‐goal tracker ---

    /** The user’s target weight (kg), or null if not set */
    val goalWeightFlow: Flow<Float?> =
        context.dataStore.data.map { prefs ->
            prefs[UserKeys.GOAL_WEIGHT]
        }

    /**
     * A map of LocalDate → List<Float>. We first try to parse the new format
     * (each date has a list of entries); on failure (old installs), we parse
     * the old Map<String, Float> and wrap each Float in a List.
     */
    val weightEntriesFlow: Flow<Map<LocalDate, List<Float>>> =
        context.dataStore.data.map { prefs ->
            prefs[UserKeys.WEIGHT_ENTRIES_JSON]?.let { json ->
                // Try new format: Map<String, List<Float>>
                val asListMap = runCatching {
                    val listType = object : TypeToken<Map<String, List<Float>>>() {}.type
                    gson.fromJson<Map<String, List<Float>>>(json, listType)
                }.getOrElse {
                    // Fallback old format: Map<String, Float> → wrap each Float
                    val floatType = object : TypeToken<Map<String, Float>>() {}.type
                    val oldMap: Map<String, Float> = gson.fromJson(json, floatType)
                    oldMap.mapValues { listOf(it.value) }
                }
                // Convert the String-keys to LocalDate
                asListMap.mapKeys { LocalDate.parse(it.key) }
            } ?: emptyMap()
        }

    /** Save or overwrite the goal weight */
    suspend fun saveGoalWeight(weight: Float) {
        context.dataStore.edit { prefs ->
            prefs[UserKeys.GOAL_WEIGHT] = weight
        }
    }

    /**
     * Append a new weight entry to the list for that date.
     * Also handles legacy JSON if detected.
     */
    suspend fun addWeightEntry(date: LocalDate, weight: Float) {
        context.dataStore.edit { prefs ->
            val existingJson = prefs[UserKeys.WEIGHT_ENTRIES_JSON]
            // Parse into mutable map of date→list
            val existingListMap: MutableMap<String, MutableList<Float>> =
                if (!existingJson.isNullOrBlank()) {
                    runCatching {
                        val newType = object : TypeToken<MutableMap<String, MutableList<Float>>>() {}.type
                        gson.fromJson<MutableMap<String, MutableList<Float>>>(existingJson, newType)
                    }.getOrElse {
                        // Fallback old format
                        val floatType = object : TypeToken<Map<String, Float>>() {}.type
                        val oldMap: Map<String, Float> = gson.fromJson(existingJson, floatType)
                        oldMap.mapValues { mutableListOf(it.value) }.toMutableMap()
                    }
                } else {
                    mutableMapOf()
                }

            // Append this new weight
            val key = date.toString()
            val listForDay = existingListMap.getOrPut(key) { mutableListOf() }
            listForDay.add(weight)

            // Save it back
            prefs[UserKeys.WEIGHT_ENTRIES_JSON] = gson.toJson(existingListMap)
        }
    }
}
