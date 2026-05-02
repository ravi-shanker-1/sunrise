package com.techpulse.features.onboarding.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Technology(
    val id: String,
    val name: String,
    val category: String,
    val iconUrl: String = "",
    val description: String = "",
    val popularity: Int = 0
)

@Serializable
data class TechSelection(
    val technologyId: String,
    val interestLevel: InterestLevel = InterestLevel.INTERESTED
)

@Serializable
enum class InterestLevel(val displayName: String) {
    CURIOUS("Curious"),
    INTERESTED("Interested"),
    FOCUSED("Focused")
}
