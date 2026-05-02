package com.techpulse.newsletter

import com.techpulse.common.dto.BaseResponse
import com.techpulse.common.exception.ApiException
import com.techpulse.user.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/newsletter")
class NewsletterController(private val newsletterService: NewsletterService) {

    @GetMapping("/weekly")
    fun getWeeklyNewsletter(authentication: Authentication): ResponseEntity<BaseResponse<Any>> {
        val user = authentication.principal as User
        return when (val result = newsletterService.getWeeklyNewsletter(user.id)) {
            is NewsletterResult.Success -> ResponseEntity.ok(BaseResponse.success(result.data))
            is NewsletterResult.NotFound -> throw ApiException(result.message, HttpStatus.NOT_FOUND)
            is NewsletterResult.Error -> throw ApiException(result.message, result.status)
        }
    }

    @GetMapping("/archive")
    fun getArchive(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        authentication: Authentication
    ): ResponseEntity<BaseResponse<Any>> {
        val user = authentication.principal as User
        return when (val result = newsletterService.getArchive(user.id, page, size)) {
            is NewsletterResult.Success -> ResponseEntity.ok(BaseResponse.success(result.data))
            is NewsletterResult.NotFound -> throw ApiException(result.message, HttpStatus.NOT_FOUND)
            is NewsletterResult.Error -> throw ApiException(result.message, result.status)
        }
    }

    @PostMapping("/items/{id}/bookmark")
    fun bookmarkItem(
        @PathVariable id: Long,
        authentication: Authentication
    ): ResponseEntity<BaseResponse<Any>> {
        val user = authentication.principal as User
        return when (val result = newsletterService.bookmarkItem(user.id, id)) {
            is NewsletterResult.Success -> ResponseEntity.ok(BaseResponse.success(result.data))
            is NewsletterResult.NotFound -> throw ApiException(result.message, HttpStatus.NOT_FOUND)
            is NewsletterResult.Error -> throw ApiException(result.message, result.status)
        }
    }
}
