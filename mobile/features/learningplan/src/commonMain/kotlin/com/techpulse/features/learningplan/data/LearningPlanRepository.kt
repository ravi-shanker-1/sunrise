package com.techpulse.features.learningplan.data

import com.techpulse.features.learningplan.domain.model.*

interface LearningPlanRepository {
    suspend fun getCurrentPlan(): LearningPlan
    suspend fun updateProgress(itemId: String, completed: Boolean): LearningPlan
}

class MockLearningPlanRepository : LearningPlanRepository {
    private var plan = LearningPlan(
        id = "plan-1",
        weekStartDate = "2026-04-27",
        items = listOf(
            LearningItem("1", "Understanding Kotlin Coroutines Flow", "Deep dive into cold and hot flows", LearningItemType.ARTICLE, "https://example.com/flows", 15, Difficulty.INTERMEDIATE),
            LearningItem("2", "Spring Boot 3.2 New Features", "Watch overview of new features", LearningItemType.VIDEO, "https://example.com/sb32", 20, Difficulty.INTERMEDIATE),
            LearningItem("3", "Build a REST API with Ktor", "Hands-on exercise building a simple API", LearningItemType.EXERCISE, "https://example.com/ktor-lab", 45, Difficulty.BEGINNER),
            LearningItem("4", "Kubernetes Pod Scheduling Deep Dive", "Advanced scheduling concepts", LearningItemType.ARTICLE, "https://example.com/k8s", 25, Difficulty.ADVANCED),
            LearningItem("5", "Compose Multiplatform Navigation", "Tutorial on navigation patterns", LearningItemType.TUTORIAL, "https://example.com/compose-nav", 30, Difficulty.INTERMEDIATE)
        ),
        streakWeeks = 4,
        completionPercentage = 0f
    )

    override suspend fun getCurrentPlan(): LearningPlan = plan

    override suspend fun updateProgress(itemId: String, completed: Boolean): LearningPlan {
        plan = plan.copy(
            items = plan.items.map { if (it.id == itemId) it.copy(isCompleted = completed) else it },
            completionPercentage = plan.items.count { it.isCompleted }.toFloat() / plan.items.size
        )
        return plan
    }
}
