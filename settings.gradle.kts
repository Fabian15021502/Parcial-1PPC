// settings.gradle.kts

pluginManagement {
    repositories {
        // Obligatorio para KSP y Android Gradle Plugin
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Parcial1PPC"
include(":app")