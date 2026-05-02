package com.techpulse.features.profile.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserProfile(
    val name: String = "Tech Enthusiast",
    val email: String = "user@example.com",
    val techStack: List<String> = listOf("Kotlin", "Spring Boot", "PostgreSQL", "Kubernetes"),
    val streakWeeks: Int = 4,
    val topicsCompleted: Int = 23,
    val newslettersRead: Int = 12
)

class ProfileViewModel {
    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile> = _profile.asStateFlow()
}
