package com.techpulse.core.common

object Constants {
    const val BASE_URL = "https://api.techpulse.dev/v1/"
    const val APP_NAME = "TechPulse"
    const val APP_VERSION = "1.0.0"

    const val MAX_TECH_SELECTIONS = 20
    const val MIN_TECH_SELECTIONS = 3

    const val DEFAULT_DELIVERY_DAY = "Monday"
    const val DEFAULT_DELIVERY_HOUR = 8

    const val CARD_CORNER_RADIUS = 16
    const val BUTTON_CORNER_RADIUS = 12
    const val CHIP_CORNER_RADIUS = 8
    const val BASE_SPACING = 4

    object Routes {
        const val WELCOME = "/welcome"
        const val TECH_STACK = "/onboarding/tech-stack"
        const val NOTIFICATION_PREFS = "/onboarding/notifications"
        const val NEWSLETTER = "/newsletter"
        const val LEARNING_PLAN = "/learning"
        const val PROFILE = "/profile"
        const val SETTINGS = "/settings"
    }

    object ApiEndpoints {
        const val NEWSLETTER_WEEKLY = "newsletter/weekly"
        const val NEWSLETTER_ARCHIVE = "newsletter/archive"
        const val LEARNING_PLANS = "learning/plans"
        const val USER_PROFILE = "user/profile"
        const val USER_PREFERENCES = "user/preferences"
        const val BOOKMARK = "bookmark"
    }
}
