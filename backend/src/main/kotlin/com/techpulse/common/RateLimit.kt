package com.techpulse.common

/**
 * Annotation placeholder for rate limiting.
 * In production, implement via AOP aspect that checks Redis counters.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RateLimit(
    val requests: Int = 60,
    val windowSeconds: Int = 60
)
