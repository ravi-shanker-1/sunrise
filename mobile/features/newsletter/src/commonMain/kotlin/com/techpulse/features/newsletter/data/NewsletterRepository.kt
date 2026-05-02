package com.techpulse.features.newsletter.data

import com.techpulse.core.network.ApiResult
import com.techpulse.features.newsletter.domain.model.NewsletterItem
import com.techpulse.features.newsletter.domain.model.NewsletterSection
import com.techpulse.features.newsletter.domain.model.WeeklyNewsletter

interface NewsletterRepository {
    suspend fun getWeeklyNewsletter(): ApiResult<WeeklyNewsletter>
    suspend fun toggleBookmark(itemId: String, bookmarked: Boolean): ApiResult<Boolean>
    suspend fun getArchive(): ApiResult<List<WeeklyNewsletter>>
}

class MockNewsletterRepository : NewsletterRepository {

    private val sampleNewsletter = WeeklyNewsletter(
        id = "nl-2024-w03",
        weekOf = "2024-01-15",
        issueNumber = 42,
        personalizedScore = 0.92f,
        sections = listOf(
            NewsletterSection.WhatsHot(
                items = listOf(
                    NewsletterItem(
                        id = "hot-1",
                        title = "Kotlin 2.0 Compiler: K2 is Stable",
                        summary = "The new K2 compiler brings up to 2x faster compilation speeds and improved type inference. Major milestone for the Kotlin ecosystem.",
                        url = "https://kotlinlang.org/docs/whatsnew20.html",
                        tags = listOf("Kotlin", "Compiler", "Performance"),
                        trendScore = 98,
                        isBookmarked = false
                    ),
                    NewsletterItem(
                        id = "hot-2",
                        title = "React Server Components in Production",
                        summary = "Next.js 14 brings React Server Components to mainstream production use. Here's what teams are learning from real-world deployments.",
                        url = "https://nextjs.org/blog",
                        tags = listOf("React", "Next.js", "Frontend"),
                        trendScore = 95,
                        isBookmarked = true
                    ),
                    NewsletterItem(
                        id = "hot-3",
                        title = "Rust in the Linux Kernel: Year One",
                        summary = "A retrospective on the first year of Rust code in the Linux kernel. What worked, what didn't, and what's next.",
                        url = "https://rust-for-linux.com",
                        tags = listOf("Rust", "Linux", "Systems"),
                        trendScore = 91,
                        isBookmarked = false
                    )
                )
            ),
            NewsletterSection.NewReleases(
                items = listOf(
                    NewsletterItem(
                        id = "rel-1",
                        title = "Compose Multiplatform 1.6",
                        summary = "JetBrains releases Compose Multiplatform 1.6 with iOS stability improvements, new navigation components, and better performance.",
                        url = "https://github.com/JetBrains/compose-multiplatform/releases",
                        tags = listOf("KMP", "Compose", "UI"),
                        version = "1.6.0",
                        isBookmarked = false
                    ),
                    NewsletterItem(
                        id = "rel-2",
                        title = "Gradle 8.6 Released",
                        summary = "Faster configuration caching, improved Kotlin DSL support, and new dependency verification features.",
                        url = "https://gradle.org/releases/",
                        tags = listOf("Gradle", "Build Tools"),
                        version = "8.6",
                        isBookmarked = false
                    ),
                    NewsletterItem(
                        id = "rel-3",
                        title = "Ktor 2.3.8",
                        summary = "New WebSocket improvements, enhanced OpenAPI support, and critical security patches for the Kotlin server framework.",
                        url = "https://ktor.io/changelog/",
                        tags = listOf("Ktor", "Backend", "Kotlin"),
                        version = "2.3.8",
                        isBookmarked = false
                    )
                )
            ),
            NewsletterSection.DeepDive(
                items = listOf(
                    NewsletterItem(
                        id = "deep-1",
                        title = "Understanding Kotlin Coroutines Internals",
                        summary = "A comprehensive guide to how Kotlin coroutines work under the hood. From CPS transformation to dispatcher mechanics, learn what makes coroutines tick and how to debug complex concurrent code.",
                        url = "https://kotlinlang.org/docs/coroutines-guide.html",
                        tags = listOf("Kotlin", "Coroutines", "Concurrency"),
                        readTimeMinutes = 25,
                        difficulty = "Advanced",
                        isBookmarked = false
                    ),
                    NewsletterItem(
                        id = "deep-2",
                        title = "Building a Design System with Compose",
                        summary = "Step-by-step guide to creating a scalable design system using Jetpack Compose. Covers theming, component architecture, accessibility, and cross-platform considerations.",
                        url = "https://developer.android.com/jetpack/compose",
                        tags = listOf("Compose", "Design System", "UI"),
                        readTimeMinutes = 18,
                        difficulty = "Intermediate",
                        isBookmarked = true
                    )
                )
            ),
            NewsletterSection.QuickTips(
                items = listOf(
                    NewsletterItem(
                        id = "tip-1",
                        title = "Use Kotlin's buildList for Conditional Items",
                        summary = "Instead of mutableListOf + if/add, use buildList { add(x); if(cond) add(y) } for cleaner conditional list building.",
                        tags = listOf("Kotlin", "Tips"),
                        isBookmarked = false
                    ),
                    NewsletterItem(
                        id = "tip-2",
                        title = "Compose: remember vs rememberSaveable",
                        summary = "Use remember for simple recomposition survival. Use rememberSaveable when state must survive configuration changes and process death.",
                        tags = listOf("Compose", "State", "Tips"),
                        isBookmarked = false
                    ),
                    NewsletterItem(
                        id = "tip-3",
                        title = "Git Worktrees for Parallel Development",
                        summary = "Use 'git worktree add' to check out multiple branches simultaneously. Perfect for reviewing PRs while working on features.",
                        tags = listOf("Git", "Productivity"),
                        isBookmarked = false
                    )
                )
            ),
            NewsletterSection.LearningPath(
                items = listOf(
                    NewsletterItem(
                        id = "learn-1",
                        title = "Step 1: KMP Project Setup",
                        summary = "Set up your first Kotlin Multiplatform project with shared business logic targeting Android and iOS.",
                        tags = listOf("KMP", "Setup"),
                        difficulty = "Beginner",
                        readTimeMinutes = 15,
                        isBookmarked = false
                    ),
                    NewsletterItem(
                        id = "learn-2",
                        title = "Step 2: Shared Networking with Ktor",
                        summary = "Implement shared API calls using Ktor client. Handle platform-specific HTTP engines and serialization.",
                        tags = listOf("KMP", "Ktor", "Networking"),
                        difficulty = "Intermediate",
                        readTimeMinutes = 20,
                        isBookmarked = false
                    ),
                    NewsletterItem(
                        id = "learn-3",
                        title = "Step 3: Compose Multiplatform UI",
                        summary = "Build shared UI with Compose Multiplatform. Learn about expect/actual for platform-specific components.",
                        tags = listOf("KMP", "Compose", "UI"),
                        difficulty = "Intermediate",
                        readTimeMinutes = 25,
                        isBookmarked = false
                    )
                )
            )
        )
    )

    private val bookmarks = mutableSetOf<String>("hot-2", "deep-2")

    override suspend fun getWeeklyNewsletter(): ApiResult<WeeklyNewsletter> {
        return ApiResult.Success(sampleNewsletter)
    }

    override suspend fun toggleBookmark(itemId: String, bookmarked: Boolean): ApiResult<Boolean> {
        if (bookmarked) bookmarks.add(itemId) else bookmarks.remove(itemId)
        return ApiResult.Success(bookmarked)
    }

    override suspend fun getArchive(): ApiResult<List<WeeklyNewsletter>> {
        return ApiResult.Success(listOf(sampleNewsletter))
    }
}
