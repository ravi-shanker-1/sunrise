package com.techpulse.user

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

enum class InterestLevel {
    BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
}

@Entity
@Table(name = "tech_stacks")
class TechStack(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long = 0,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    val technologies: List<String> = emptyList(),

    @Enumerated(EnumType.STRING)
    @Column(name = "interest_level", nullable = false)
    val interestLevel: InterestLevel = InterestLevel.BEGINNER
)
