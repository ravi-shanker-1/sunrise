package com.techpulse.learningplan

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LearningPlanRepository : JpaRepository<LearningPlan, Long> {
    fun findByUserIdAndStatus(userId: Long, status: PlanStatus): List<LearningPlan>
    fun findFirstByUserIdAndStatusOrderByUpdatedAtDesc(userId: Long, status: PlanStatus): LearningPlan?
}
