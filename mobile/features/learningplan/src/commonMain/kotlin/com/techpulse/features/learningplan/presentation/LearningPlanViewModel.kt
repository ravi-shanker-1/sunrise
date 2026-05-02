package com.techpulse.features.learningplan.presentation

import com.techpulse.features.learningplan.data.LearningPlanRepository
import com.techpulse.features.learningplan.domain.model.LearningPlan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LearningPlanUiState {
    data object Loading : LearningPlanUiState()
    data class Success(val plan: LearningPlan) : LearningPlanUiState()
    data class Error(val message: String) : LearningPlanUiState()
}

class LearningPlanViewModel(
    private val repository: LearningPlanRepository
) {
    private val scope = CoroutineScope(Dispatchers.Main)
    private val _uiState = MutableStateFlow<LearningPlanUiState>(LearningPlanUiState.Loading)
    val uiState: StateFlow<LearningPlanUiState> = _uiState.asStateFlow()

    fun loadPlan() {
        scope.launch {
            _uiState.value = LearningPlanUiState.Loading
            try {
                val plan = repository.getCurrentPlan()
                _uiState.value = LearningPlanUiState.Success(plan)
            } catch (e: Exception) {
                _uiState.value = LearningPlanUiState.Error(e.message ?: "Failed to load learning plan")
            }
        }
    }

    fun toggleItemCompletion(itemId: String, completed: Boolean) {
        scope.launch {
            try {
                val updatedPlan = repository.updateProgress(itemId, completed)
                _uiState.value = LearningPlanUiState.Success(updatedPlan)
            } catch (e: Exception) {
                // Keep current state on error
            }
        }
    }
}
