pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TechPulse"

include(":composeApp")
include(":androidApp")
include(":core:network")
include(":core:database")
include(":core:common")
include(":core:designsystem")
include(":features:onboarding")
include(":features:newsletter")
include(":features:learningplan")
include(":features:profile")
include(":features:settings")
