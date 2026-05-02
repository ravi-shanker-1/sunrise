package com.techpulse.features.newsletter.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class NewsletterSection {
    abstract val title: String
    abstract val items: List<NewsletterItem>

    @Serializable
    data class WhatsHot(override val title: String = "What's Hot 🔥", override val items: List<NewsletterItem> = emptyList()) : NewsletterSection()
    @Serializable
    data class NewReleases(override val title: String = "New Releases 🚀", override val items: List<NewsletterItem> = emptyList()) : NewsletterSection()
    @Serializable
    data class DeepDive(override val title: String = "Deep Dive 🔮", override val items: List<NewsletterItem> = emptyList()) : NewsletterSection()
    @Serializable
    data class QuickTips(override val title: String = "Quick Tips ⚡", override val items: List<NewsletterItem> = emptyList()) : NewsletterSection()
    @Serializable
    data class LearningPath(override val title: String = "Learning Path 📚", override val items: List<NewsletterItem> = emptyList()) : NewsletterSection()
}
