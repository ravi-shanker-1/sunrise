package com.techpulse.features.newsletter.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsletterResponse(
    @SerialName("id") val id: String,
    @SerialName("week_of") val weekOf: String,
    @SerialName("issue_number") val issueNumber: Int,
    @SerialName("sections") val sections: List<SectionResponse> = emptyList()
)

@Serializable
data class SectionResponse(
    @SerialName("type") val type: String,
    @SerialName("title") val title: String,
    @SerialName("items") val items: List<ItemResponse> = emptyList()
)

@Serializable
data class ItemResponse(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("summary") val summary: String,
    @SerialName("url") val url: String = "",
    @SerialName("image_url") val imageUrl: String = "",
    @SerialName("tags") val tags: List<String> = emptyList(),
    @SerialName("trend_score") val trendScore: Int = 0,
    @SerialName("version") val version: String = "",
    @SerialName("read_time_minutes") val readTimeMinutes: Int = 0,
    @SerialName("difficulty") val difficulty: String = ""
)
