package com.techpulse.features.onboarding.presentation

import com.techpulse.features.onboarding.data.OnboardingRepository
import com.techpulse.features.onboarding.domain.model.InterestLevel
import com.techpulse.features.onboarding.domain.model.TechCategory
import com.techpulse.features.onboarding.domain.model.TechSelection
import com.techpulse.features.onboarding.domain.model.Technology
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class OnboardingUiState(
    val currentStep: Int = 0,
    val availableTechnologies: Map<TechCategory, List<Technology>> = emptyMap(),
    val selectedTechs: Set<TechSelection> = emptySet(),
    val deliveryDay: String = "Monday",
    val notificationsEnabled: Boolean = true,
    val isComplete: Boolean = false
)

class OnboardingViewModel(
    private val repository: OnboardingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    init {
        loadTechnologies()
    }

    private fun loadTechnologies() {
        viewModelScope.launch {
            val technologies = repository.getAvailableTechnologies()
            val grouped = technologies.groupBy { tech ->
                TechCategory.fromName(tech.category) ?: TechCategory.LANGUAGES
            }
            _uiState.update { it.copy(availableTechnologies = grouped) }
        }
    }

    fun nextStep() {
        _uiState.update { state ->
            val next = (state.currentStep + 1).coerceAtMost(2)
            state.copy(currentStep = next)
        }
    }

    fun previousStep() {
        _uiState.update { state ->
            val prev = (state.currentStep - 1).coerceAtLeast(0)
            state.copy(currentStep = prev)
        }
    }

    fun toggleTechnology(technologyId: String) {
        _uiState.update { state ->
            val existing = state.selectedTechs.find { it.technologyId == technologyId }
            val updated = if (existing != null) {
                state.selectedTechs - existing
            } else {
                state.selectedTechs + TechSelection(technologyId)
            }
            state.copy(selectedTechs = updated)
        }
    }

    fun setInterestLevel(techId: String, level: InterestLevel) {
        _uiState.update { state ->
            val updated = state.selectedTechs.map { selection ->
                if (selection.technologyId == techId) {
                    selection.copy(interestLevel = level)
                } else {
                    selection
                }
            }.toSet()
            state.copy(selectedTechs = updated)
        }
    }

    fun setDeliveryDay(day: String) {
        _uiState.update { it.copy(deliveryDay = day) }
    }

    fun toggleNotifications() {
        _uiState.update { it.copy(notificationsEnabled = !it.notificationsEnabled) }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            val state = _uiState.value
            repository.saveTechStack(state.selectedTechs)
            repository.saveDeliveryPreferences(state.deliveryDay, state.notificationsEnabled)
            repository.markOnboardingComplete()
            _uiState.update { it.copy(isComplete = true) }
        }
    }
}
