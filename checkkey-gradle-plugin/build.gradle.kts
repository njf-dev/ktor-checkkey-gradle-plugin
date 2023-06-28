import org.jetbrains.kotlin.konan.properties.loadProperties

val properties = loadProperties("${rootDir.parent}/gradle.properties")
val ktor_version: String by properties

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
}

val GROUP_ID="com.njf2016.plugin"
group = GROUP_ID

repositories {
    gradlePluginPortal()
    mavenLocal()
    mavenCentral()
}

gradlePlugin {
    plugins.create("checkKey"){
        id = "com.njf2016.plugin.ktor-checkkey"
        implementationClass = "com.njf2016.plugin.CheckKey"
    }
}

java {
    withSourcesJar()
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = GROUP_ID
                artifactId = project.name
                version = "1.0.0"
                from(components["java"])
            }
        }
        repositories {
            maven {
                name = "XXX"
                url = uri("${rootProject.buildDir}/maven")
            }
        }
    }
}

dependencies {
    implementation("io.github.config4k:config4k:0.5.0")
    implementation("io.ktor:ktor-network-tls-certificates:$ktor_version")
    implementation("io.ktor:ktor-network-tls-jvm:$ktor_version")
}
