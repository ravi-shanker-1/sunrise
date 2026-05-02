package com.techpulse.newsletter

import com.techpulse.common.dto.PagedResponse
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

sealed class NewsletterResult<out T> {
    data class Success<T>(val data: T) : NewsletterResult<T>()
    data class NotFound(val message: String) : NewsletterResult<Nothing>()
    data class Error(val message: String, val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR) : NewsletterResult<Nothing>()
}

@Service
class NewsletterService(
    private val newsletterRepository: NewsletterRepository
) {

    @Cacheable(value = ["newsletters"], key = "'weekly:' + #userId")
    fun getWeeklyNewsletter(userId: Long): NewsletterResult<Newsletter> {
        val weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val newsletter = newsletterRepository.findByUserIdAndWeekStartDate(userId, weekStart)
            ?: return NewsletterResult.NotFound("No newsletter available for this week yet")
        return NewsletterResult.Success(newsletter)
    }

    fun getArchive(userId: Long, page: Int, size: Int): NewsletterResult<PagedResponse<Newsletter>> {
        val pageable = PageRequest.of(page.coerceAtLeast(0), size.coerceIn(1, 50))
        val newsletters = newsletterRepository.findByUserIdOrderByWeekStartDateDesc(userId, pageable)
        return NewsletterResult.Success(
            PagedResponse(
                content = newsletters.content,
                page = newsletters.number,
                size = newsletters.size,
                totalElements = newsletters.totalElements,
                totalPages = newsletters.totalPages
            )
        )
    }

    fun bookmarkItem(userId: Long, itemId: Long): NewsletterResult<Map<String, Any>> {
        // Placeholder: in production, this would persist a bookmark record
        return NewsletterResult.Success(
            mapOf("itemId" to itemId, "bookmarked" to true)
        )
    }
}
