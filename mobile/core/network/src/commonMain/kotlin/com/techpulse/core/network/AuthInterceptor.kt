package com.techpulse.core.network

class AuthInterceptor {
    private var token: String? = null

    fun setToken(newToken: String) { token = newToken }
    fun getToken(): String? = token
    fun clearToken() { token = null }
    fun hasToken(): Boolean = token != null
    fun getAuthorizationHeader(): String? = token?.let { "Bearer $it" }
}
