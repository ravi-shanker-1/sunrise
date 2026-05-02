package com.techpulse.features.learningplan.domain.model

data class LearningPlan(
    val id: String,
    val weekStartDate: String,
    val items: List<LearningItem>,
    val streakWeeks: Int,
    val completionPercentage: Float
) {
    val completedCount: Int get() = items.count { it.isCompleted }
    val totalCount: Int get() = items.size
}
