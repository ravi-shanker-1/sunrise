package com.techpulse.features.onboarding.domain.model

enum class TechCategory(val displayName: String, val icon: String) {
    LANGUAGES("Languages", "💻"),
    FRAMEWORKS("Frameworks", "🏗️"),
    CLOUD("Cloud & Infrastructure", "☁️"),
    DEVOPS("DevOps & CI/CD", "🔧"),
    DATA("Data & Databases", "🗄️"),
    AI_ML("AI & Machine Learning", "🤖");

    companion object {
        fun fromName(name: String): TechCategory? = entries.find { it.name == name }
    }
}
