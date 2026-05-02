package com.techpulse.core.network

import com.techpulse.core.common.Constants
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ApiClient(private val authInterceptor: AuthInterceptor) {

    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = false
        encodeDefaults = true
        coerceInputValues = true
    }

    val httpClient = HttpClient {
        install(ContentNegotiation) { json(json) }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        defaultRequest {
            url(Constants.BASE_URL)
            contentType(ContentType.Application.Json)
            authInterceptor.getAuthorizationHeader()?.let {
                header(HttpHeaders.Authorization, it)
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 15_000
        }
    }
}
