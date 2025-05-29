// app/src/main/java/com/nutrimove/ui/screens/treinos/TrainingViewModel.kt
package com.nutrimove.ui.screens.treinos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimove.data.UserPreferences
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Make sure these imports match where you defined them:
import com.nutrimove.ui.screens.treinos.generateSplit
import com.nutrimove.ui.screens.treinos.WorkoutDay

/**
 * ViewModel for the Training feature.
 * Observes the stored number of training days and generates the workout split,
 * and emits navigation events when the user taps a day.
 */
class TrainingViewModel(application: Application) : AndroidViewModel(application) {

    // DataStore-backed prefs to load the number of training days (default = 3)
    private val prefs = UserPreferences(application)

    // Expose trainingDays as a StateFlow
    val trainingDays: StateFlow<Int> = prefs.trainingDaysFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 3
        )

    // Generate the split whenever trainingDays changes
    val split: StateFlow<List<WorkoutDay>> = trainingDays
        .map { days -> generateSplit(days) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = generateSplit(3)
        )

    // Navigation events: emits the selected WorkoutDay
    private val _navigateToDay = MutableSharedFlow<WorkoutDay>()
    val navigateToDay: SharedFlow<WorkoutDay> = _navigateToDay.asSharedFlow()

    /** Call when the user taps on a day card. */
    fun onDaySelected(day: WorkoutDay) {
        viewModelScope.launch {
            _navigateToDay.emit(day)
        }
    }
}
