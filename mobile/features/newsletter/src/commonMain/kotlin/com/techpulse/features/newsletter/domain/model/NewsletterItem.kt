package com.techpulse.features.newsletter.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsletterItem(
    val id: String,
    val title: String,
    val summary: String,
    val url: String = "",
    val imageUrl: String = "",
    val tags: List<String> = emptyList(),
    val trendScore: Int = 0,
    val version: String = "",
    val isBookmarked: Boolean = false,
    val readTimeMinutes: Int = 0,
    val difficulty: String = ""
)

@Serializable
data class WeeklyNewsletter(
    val id: String,
    val weekOf: String,
    val issueNumber: Int,
    val sections: List<NewsletterSection> = emptyList(),
    val personalizedScore: Float = 0f
)
