package com.techpulse.features.newsletter.presentation

import com.techpulse.core.network.ApiResult
import com.techpulse.features.newsletter.domain.model.WeeklyNewsletter
import com.techpulse.features.newsletter.domain.usecase.BookmarkItemUseCase
import com.techpulse.features.newsletter.domain.usecase.GetWeeklyNewsletterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

sealed class NewsletterUiState {
    data object Loading : NewsletterUiState()
    data class Success(val newsletter: WeeklyNewsletter) : NewsletterUiState()
    data class Error(val message: String) : NewsletterUiState()
}

class NewsletterViewModel(
    private val getWeeklyNewsletterUseCase: GetWeeklyNewsletterUseCase,
    private val bookmarkItemUseCase: BookmarkItemUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<NewsletterUiState>(NewsletterUiState.Loading)
    val uiState: StateFlow<NewsletterUiState> = _uiState.asStateFlow()

    init {
        loadNewsletter()
    }

    fun loadNewsletter() {
        viewModelScope.launch {
            _uiState.value = NewsletterUiState.Loading
            when (val result = getWeeklyNewsletterUseCase()) {
                is ApiResult.Success -> {
                    _uiState.value = NewsletterUiState.Success(result.data)
                }
                is ApiResult.Error -> {
                    _uiState.value = NewsletterUiState.Error(result.message)
                }
            }
        }
    }

    fun toggleBookmark(itemId: String) {
        val currentState = _uiState.value
        if (currentState is NewsletterUiState.Success) {
            val newsletter = currentState.newsletter
            val updatedSections = newsletter.sections.map { section ->
                val updatedItems = section.items.map { item ->
                    if (item.id == itemId) item.copy(isBookmarked = !item.isBookmarked) else item
                }
                when (section) {
                    is com.techpulse.features.newsletter.domain.model.NewsletterSection.WhatsHot -> section.copy(items = updatedItems)
                    is com.techpulse.features.newsletter.domain.model.NewsletterSection.NewReleases -> section.copy(items = updatedItems)
                    is com.techpulse.features.newsletter.domain.model.NewsletterSection.DeepDive -> section.copy(items = updatedItems)
                    is com.techpulse.features.newsletter.domain.model.NewsletterSection.QuickTips -> section.copy(items = updatedItems)
                    is com.techpulse.features.newsletter.domain.model.NewsletterSection.LearningPath -> section.copy(items = updatedItems)
                }
            }
            _uiState.value = NewsletterUiState.Success(newsletter.copy(sections = updatedSections))

            viewModelScope.launch {
                val item = newsletter.sections.flatMap { it.items }.find { it.id == itemId }
                item?.let { bookmarkItemUseCase(itemId, !it.isBookmarked) }
            }
        }
    }

    fun retry() {
        loadNewsletter()
    }
}
