//buildscript {
//    // 插件库还没有发布到 Gradle Plugin Portal，外部使用的时候需要通过 classpath 声明插件库
//    dependencies {
//        classpath("com.njf2016:ktor-checkkey-gradle-plugin:1.0.0")
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