package com.techpulse.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtConfig(
    @Value("\${techpulse.jwt.secret}") private val secret: String,
    @Value("\${techpulse.jwt.expiration-ms}") private val expirationMs: Long
) {

    private val key: SecretKey by lazy {
        Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun generateToken(userId: Long, email: String): String {
        val now = Date()
        val expiry = Date(now.time + expirationMs)
        return Jwts.builder()
            .subject(userId.toString())
            .claim("email", email)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean =
        runCatching { extractAllClaims(token) }.isSuccess

    fun extractUserId(token: String): Long =
        extractAllClaims(token).subject.toLong()

    fun extractEmail(token: String): String =
        extractAllClaims(token)["email"] as String

    private fun extractAllClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
}
