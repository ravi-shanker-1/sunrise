package com.techpulse.features.newsletter.domain.usecase

import com.techpulse.core.network.ApiResult
import com.techpulse.features.newsletter.data.NewsletterRepository

class BookmarkItemUseCase(private val repository: NewsletterRepository) {
    suspend operator fun invoke(itemId: String, bookmarked: Boolean): ApiResult<Boolean> =
        repository.toggleBookmark(itemId, bookmarked)
}
