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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "mobile_lab"
include(":app-toast-lab2")
project(":app-toast-lab2").projectDir = file("app-toast")
include(":app-logging-lab2")
project(":app-logging-lab2").projectDir = file("app-logging")
include(":app-attributes-lab2")
project(":app-attributes-lab2").projectDir = file("app-attributes")
include(":composearticle_lab3")
include(":taskmanager_lab3")
include(":composequadrant_lab3")
include(":myinformation_lab3")
include(":topics_lab4")
include(":tipcalculator_lab5")
include(":artspace_lab5")
