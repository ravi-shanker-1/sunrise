package com.techpulse.features.onboarding.data

import com.techpulse.features.onboarding.domain.model.InterestLevel
import com.techpulse.features.onboarding.domain.model.TechCategory
import com.techpulse.features.onboarding.domain.model.TechSelection
import com.techpulse.features.onboarding.domain.model.Technology

interface OnboardingRepository {
    suspend fun saveTechStack(selections: Set<TechSelection>)
    suspend fun getTechStack(): Set<TechSelection>
    suspend fun saveDeliveryPreferences(day: String, notificationsEnabled: Boolean)
    suspend fun getAvailableTechnologies(): List<Technology>
    suspend fun isOnboardingComplete(): Boolean
    suspend fun markOnboardingComplete()
}

class OnboardingRepositoryImpl : OnboardingRepository {

    private var savedSelections: Set<TechSelection> = emptySet()
    private var deliveryDay: String = "Monday"
    private var notificationsEnabled: Boolean = true
    private var onboardingComplete: Boolean = false

    override suspend fun saveTechStack(selections: Set<TechSelection>) {
        savedSelections = selections
    }

    override suspend fun getTechStack(): Set<TechSelection> {
        return savedSelections
    }

    override suspend fun saveDeliveryPreferences(day: String, notificationsEnabled: Boolean) {
        this.deliveryDay = day
        this.notificationsEnabled = notificationsEnabled
    }

    override suspend fun getAvailableTechnologies(): List<Technology> = availableTechnologies

    override suspend fun isOnboardingComplete(): Boolean = onboardingComplete

    override suspend fun markOnboardingComplete() {
        onboardingComplete = true
    }

    companion object {
        private val availableTechnologies = listOf(
            // Languages
            Technology(
                id = "kotlin",
                name = "Kotlin",
                category = TechCategory.LANGUAGES.name,
                description = "Modern, concise JVM language by JetBrains",
                popularity = 85
            ),
            Technology(
                id = "swift",
                name = "Swift",
                category = TechCategory.LANGUAGES.name,
                description = "Apple's powerful and intuitive programming language",
                popularity = 80
            ),
            Technology(
                id = "python",
                name = "Python",
                category = TechCategory.LANGUAGES.name,
                description = "Versatile language for web, data science, and AI",
                popularity = 95
            ),
            Technology(
                id = "typescript",
                name = "TypeScript",
                category = TechCategory.LANGUAGES.name,
                description = "Typed superset of JavaScript",
                popularity = 90
            ),
            Technology(
                id = "rust",
                name = "Rust",
                category = TechCategory.LANGUAGES.name,
                description = "Performance and memory safety without garbage collection",
                popularity = 75
            ),
            Technology(
                id = "go",
                name = "Go",
                category = TechCategory.LANGUAGES.name,
                description = "Simple, reliable, and efficient language by Google",
                popularity = 78
            ),

            // Frameworks
            Technology(
                id = "react",
                name = "React",
                category = TechCategory.FRAMEWORKS.name,
                description = "JavaScript library for building user interfaces",
                popularity = 95
            ),
            Technology(
                id = "flutter",
                name = "Flutter",
                category = TechCategory.FRAMEWORKS.name,
                description = "Google's UI toolkit for cross-platform apps",
                popularity = 82
            ),
            Technology(
                id = "spring-boot",
                name = "Spring Boot",
                category = TechCategory.FRAMEWORKS.name,
                description = "Java-based framework for production-ready apps",
                popularity = 85
            ),
            Technology(
                id = "nextjs",
                name = "Next.js",
                category = TechCategory.FRAMEWORKS.name,
                description = "React framework for production web apps",
                popularity = 88
            ),

            // Cloud
            Technology(
                id = "aws",
                name = "AWS",
                category = TechCategory.CLOUD.name,
                description = "Amazon Web Services cloud platform",
                popularity = 95
            ),
            Technology(
                id = "gcp",
                name = "GCP",
                category = TechCategory.CLOUD.name,
                description = "Google Cloud Platform",
                popularity = 80
            ),
            Technology(
                id = "azure",
                name = "Azure",
                category = TechCategory.CLOUD.name,
                description = "Microsoft's cloud computing platform",
                popularity = 85
            ),

            // DevOps
            Technology(
                id = "docker",
                name = "Docker",
                category = TechCategory.DEVOPS.name,
                description = "Containerization platform",
                popularity = 92
            ),
            Technology(
                id = "kubernetes",
                name = "Kubernetes",
                category = TechCategory.DEVOPS.name,
                description = "Container orchestration system",
                popularity = 88
            ),
            Technology(
                id = "github-actions",
                name = "GitHub Actions",
                category = TechCategory.DEVOPS.name,
                description = "CI/CD automation built into GitHub",
                popularity = 85
            ),

            // Data
            Technology(
                id = "postgresql",
                name = "PostgreSQL",
                category = TechCategory.DATA.name,
                description = "Advanced open-source relational database",
                popularity = 90
            ),
            Technology(
                id = "mongodb",
                name = "MongoDB",
                category = TechCategory.DATA.name,
                description = "Document-oriented NoSQL database",
                popularity = 82
            ),
            Technology(
                id = "redis",
                name = "Redis",
                category = TechCategory.DATA.name,
                description = "In-memory data structure store",
                popularity = 85
            ),

            // AI/ML
            Technology(
                id = "tensorflow",
                name = "TensorFlow",
                category = TechCategory.AI_ML.name,
                description = "Open-source machine learning framework",
                popularity = 88
            ),
            Technology(
                id = "pytorch",
                name = "PyTorch",
                category = TechCategory.AI_ML.name,
                description = "Deep learning framework by Meta",
                popularity = 90
            ),
            Technology(
                id = "chatgpt-api",
                name = "ChatGPT API",
                category = TechCategory.AI_ML.name,
                description = "OpenAI's conversational AI API",
                popularity = 92
            ),
        )
    }
}
