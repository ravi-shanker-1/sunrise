package com.techpulse.core.database

interface DatabaseDriverFactory {
    fun createDriver(): Any
}

class InMemoryCache {
    private val cache = mutableMapOf<String, Any>()

    fun <T : Any> put(key: String, value: T) { cache[key] = value }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(key: String): T? = cache[key] as? T

    fun remove(key: String) { cache.remove(key) }
    fun clear() { cache.clear() }
    fun containsKey(key: String): Boolean = cache.containsKey(key)
}
