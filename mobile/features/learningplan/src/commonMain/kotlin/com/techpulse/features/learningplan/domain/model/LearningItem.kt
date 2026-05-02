package com.techpulse.features.learningplan.domain.model

data class LearningItem(
    val id: String,
    val title: String,
    val description: String,
    val type: LearningItemType,
    val url: String,
    val estimatedMinutes: Int,
    val difficulty: Difficulty,
    val isCompleted: Boolean = false
)

enum class LearningItemType {
    ARTICLE, VIDEO, EXERCISE, TUTORIAL, DOCUMENTATION
}

enum class Difficulty {
    BEGINNER, INTERMEDIATE, ADVANCED
}
