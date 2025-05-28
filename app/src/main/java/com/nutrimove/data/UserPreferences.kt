package com.nutrimove.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserKeys {
    val NAME = stringPreferencesKey("name")
    val AGE = intPreferencesKey("age")
    val GOAL = stringPreferencesKey("goal")
    val HEIGHT = intPreferencesKey("height")
    val WEIGHT = intPreferencesKey("weight")
    val ACTIVITY = stringPreferencesKey("activity")
}

class UserPreferences(private val context: Context) {

    suspend fun saveUserData(name: String, age: Int, goal: String, height: Int, weight: Int, activity: String) {
        context.dataStore.edit { prefs ->
            prefs[UserKeys.NAME] = name
            prefs[UserKeys.AGE] = age
            prefs[UserKeys.GOAL] = goal
            prefs[UserKeys.HEIGHT] = height
            prefs[UserKeys.WEIGHT] = weight
            prefs[UserKeys.ACTIVITY] = activity
        }
    }

    val userFlow: Flow<Map<Preferences.Key<*>, Any>> = context.dataStore.data.map { prefs ->
        prefs.asMap()
    }
}
