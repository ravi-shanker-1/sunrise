package com.techpulse.auth

import com.techpulse.auth.dto.LoginRequest
import com.techpulse.auth.dto.RegisterRequest
import com.techpulse.common.dto.BaseResponse
import com.techpulse.common.exception.ApiException
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<BaseResponse<Any>> =
        when (val result = authService.register(request)) {
            is AuthResult.Success -> ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(result.response))
            is AuthResult.Failure -> throw ApiException(result.message, result.status)
        }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<BaseResponse<Any>> =
        when (val result = authService.login(request)) {
            is AuthResult.Success -> ResponseEntity.ok(BaseResponse.success(result.response))
            is AuthResult.Failure -> throw ApiException(result.message, result.status)
        }
}
