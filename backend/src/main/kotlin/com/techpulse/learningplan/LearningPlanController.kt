package com.techpulse.learningplan

import com.techpulse.common.dto.BaseResponse
import com.techpulse.common.exception.ApiException
import com.techpulse.user.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/learning-plan")
class LearningPlanController(private val learningPlanService: LearningPlanService) {

    @GetMapping("/current")
    fun getCurrentPlan(authentication: Authentication): ResponseEntity<BaseResponse<Any>> {
        val user = authentication.principal as User
        return when (val result = learningPlanService.getCurrentPlan(user.id)) {
            is LearningPlanResult.Success -> ResponseEntity.ok(BaseResponse.success(result.data))
            is LearningPlanResult.NotFound -> throw ApiException(result.message, HttpStatus.NOT_FOUND)
            is LearningPlanResult.ValidationError -> throw ApiException(result.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("/progress")
    fun updateProgress(
        @RequestBody request: ProgressUpdateRequest,
        authentication: Authentication
    ): ResponseEntity<BaseResponse<Any>> {
        val user = authentication.principal as User
        return when (val result = learningPlanService.updateProgress(user.id, request)) {
            is LearningPlanResult.Success -> ResponseEntity.ok(BaseResponse.success(result.data))
            is LearningPlanResult.NotFound -> throw ApiException(result.message, HttpStatus.NOT_FOUND)
            is LearningPlanResult.ValidationError -> throw ApiException(result.message, HttpStatus.BAD_REQUEST)
        }
    }
}
