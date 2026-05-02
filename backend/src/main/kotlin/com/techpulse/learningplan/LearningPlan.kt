package com.techpulse.learningplan

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.Instant

enum class PlanStatus {
    ACTIVE, COMPLETED, PAUSED, ARCHIVED
}

@Entity
@Table(name = "learning_plans")
class LearningPlan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long = 0,

    @Column(nullable = false)
    val title: String = "",

    @Column(columnDefinition = "text")
    val description: String = "",

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    val milestones: List<Map<String, Any>> = emptyList(),

    @Column(name = "progress_pct", nullable = false)
    val progressPct: Int = 0,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: PlanStatus = PlanStatus.ACTIVE,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now()
)
