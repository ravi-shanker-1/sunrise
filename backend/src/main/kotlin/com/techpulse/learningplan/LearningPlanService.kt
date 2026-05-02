package com.techpulse.learningplan

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

data class ProgressUpdateRequest(
    val progressPct: Int,
    val status: PlanStatus? = null
)

sealed class LearningPlanResult<out T> {
    data class Success<T>(val data: T) : LearningPlanResult<T>()
    data class NotFound(val message: String) : LearningPlanResult<Nothing>()
    data class ValidationError(val message: String) : LearningPlanResult<Nothing>()
}

@Service
class LearningPlanService(
    private val learningPlanRepository: LearningPlanRepository
) {

    fun getCurrentPlan(userId: Long): LearningPlanResult<LearningPlan> {
        val plan = learningPlanRepository.findFirstByUserIdAndStatusOrderByUpdatedAtDesc(
            userId, PlanStatus.ACTIVE
        ) ?: return LearningPlanResult.NotFound("No active learning plan found")

        return LearningPlanResult.Success(plan)
    }

    @Transactional
    fun updateProgress(userId: Long, request: ProgressUpdateRequest): LearningPlanResult<LearningPlan> {
        if (request.progressPct !in 0..100) {
            return LearningPlanResult.ValidationError("Progress must be between 0 and 100")
        }

        val plan = learningPlanRepository.findFirstByUserIdAndStatusOrderByUpdatedAtDesc(
            userId, PlanStatus.ACTIVE
        ) ?: return LearningPlanResult.NotFound("No active learning plan found")

        val newStatus = when {
            request.status != null -> request.status
            request.progressPct >= 100 -> PlanStatus.COMPLETED
            else -> plan.status
        }

        val updated = learningPlanRepository.save(
            LearningPlan(
                id = plan.id,
                userId = plan.userId,
                title = plan.title,
                description = plan.description,
                milestones = plan.milestones,
                progressPct = request.progressPct,
                status = newStatus,
                createdAt = plan.createdAt,
                updatedAt = Instant.now()
            )
        )

        return LearningPlanResult.Success(updated)
    }
}
