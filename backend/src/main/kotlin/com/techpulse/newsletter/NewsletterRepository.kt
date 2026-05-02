package com.techpulse.newsletter

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface NewsletterRepository : JpaRepository<Newsletter, Long> {
    fun findByUserIdAndWeekStartDate(userId: Long, weekStartDate: LocalDate): Newsletter?
    fun findByUserIdOrderByWeekStartDateDesc(userId: Long, pageable: Pageable): Page<Newsletter>
}
