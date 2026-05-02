package com.techpulse.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TechStackRepository : JpaRepository<TechStack, Long> {
    fun findByUserId(userId: Long): TechStack?
}
