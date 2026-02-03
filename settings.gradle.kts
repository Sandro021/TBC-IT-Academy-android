pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Homework-39"
include(":app")
include(":core:data")
include(":feature")
include(":feature:field")
include(":feature:field:presentation")
include(":core:ui:theme")
include(":feature:field:data")
include(":feature:field:domain")

include(":core:resource")
include(":feature:field:di")
include(":feature:field:domain:GetRegistrationFieldUseCaseTest")
include(":core:domain")
