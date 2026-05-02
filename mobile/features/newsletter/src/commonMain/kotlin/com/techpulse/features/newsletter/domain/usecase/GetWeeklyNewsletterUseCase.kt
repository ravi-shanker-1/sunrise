package com.techpulse.features.newsletter.domain.usecase

import com.techpulse.core.network.ApiResult
import com.techpulse.features.newsletter.data.NewsletterRepository
import com.techpulse.features.newsletter.domain.model.WeeklyNewsletter

class GetWeeklyNewsletterUseCase(private val repository: NewsletterRepository) {
    suspend operator fun invoke(): ApiResult<WeeklyNewsletter> = repository.getWeeklyNewsletter()
}
