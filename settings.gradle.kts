pluginManagement {

    includeBuild("checkkey-gradle-plugin")
    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        google()
        maven("https://jitpack.io")
    }

    val kotlin_version:String by settings
    val ktor_version:String by settings
    val kotlin_serialization_version:String by settings
    plugins {
        kotlin("jvm") version kotlin_version
        id("io.ktor.plugin") version ktor_version
        id("org.jetbrains.kotlin.plugin.serialization") version kotlin_serialization_version
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
    }
}

rootProject.name = "ktor-checkkey-gradle-plugin"

include(":app")