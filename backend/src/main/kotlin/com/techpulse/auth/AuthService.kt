package com.techpulse.auth

import com.techpulse.auth.dto.AuthResponse
import com.techpulse.auth.dto.LoginRequest
import com.techpulse.auth.dto.RegisterRequest
import com.techpulse.common.exception.ApiException
import com.techpulse.config.JwtConfig
import com.techpulse.user.User
import com.techpulse.user.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

sealed class AuthResult {
    data class Success(val response: AuthResponse) : AuthResult()
    data class Failure(val message: String, val status: HttpStatus) : AuthResult()
}

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtConfig: JwtConfig
) {

    @Transactional
    fun register(request: RegisterRequest): AuthResult {
        if (userRepository.existsByEmail(request.email)) {
            return AuthResult.Failure("Email already registered", HttpStatus.CONFLICT)
        }

        val user = User(
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            name = request.name,
            createdAt = Instant.now()
        )
        val saved = userRepository.save(user)
        val token = jwtConfig.generateToken(saved.id, saved.email)

        return AuthResult.Success(
            AuthResponse(
                token = token,
                userId = saved.id,
                email = saved.email,
                name = saved.name
            )
        )
    }

    fun login(request: LoginRequest): AuthResult {
        val user = userRepository.findByEmail(request.email)
            ?: return AuthResult.Failure("Invalid credentials", HttpStatus.UNAUTHORIZED)

        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            return AuthResult.Failure("Invalid credentials", HttpStatus.UNAUTHORIZED)
        }

        val token = jwtConfig.generateToken(user.id, user.email)
        return AuthResult.Success(
            AuthResponse(
                token = token,
                userId = user.id,
                email = user.email,
                name = user.name
            )
        )
    }
}
