package com.techpulse.user

import com.techpulse.common.dto.BaseResponse
import com.techpulse.common.exception.ApiException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(private val userService: UserService) {

    @PutMapping("/techstack")
    fun updateTechStack(
        @RequestBody request: TechStackRequest,
        authentication: Authentication
    ): ResponseEntity<BaseResponse<Any>> {
        val user = authentication.principal as User
        return when (val result = userService.updateTechStack(user.id, request)) {
            is UserResult.Success -> ResponseEntity.ok(BaseResponse.success(result.data))
            is UserResult.NotFound -> throw ApiException(result.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/stats")
    fun getUserStats(authentication: Authentication): ResponseEntity<BaseResponse<Any>> {
        val user = authentication.principal as User
        return when (val result = userService.getUserStats(user.id)) {
            is UserResult.Success -> ResponseEntity.ok(BaseResponse.success(result.data))
            is UserResult.NotFound -> throw ApiException(result.message, HttpStatus.NOT_FOUND)
        }
    }
}
