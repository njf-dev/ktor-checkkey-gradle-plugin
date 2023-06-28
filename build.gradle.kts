//buildscript {
//    dependencies {
//        classpath("com.github.njf-dev:ktor-checkkey-gradle-plugin:0.0.1")
//    }
//}

plugins {
    kotlin("jvm") apply false
    id("io.ktor.plugin") apply false
    id("org.jetbrains.kotlin.plugin.serialization") apply false
}

//tasks.register<Delete>("clean") {
//    delete(rootProject.buildDir)
//}