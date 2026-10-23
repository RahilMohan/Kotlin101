import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.4.21"
    kotlin("plugin.serialization") version "2.4.21"
    id("org.jetbrains.compose") version "1.12.2"
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.21"
    application
}

group = "com.vibe.kotlin101"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.tech/compose/release")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.compose.material3:material3:1.12.0-alpha03")
    implementation("org.jetbrains.compose.foundation:foundation:1.12.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.12.0")

    // For Navigation in Compose Multiplatform - updated to RC
    implementation("org.jetbrains.androidx.navigation:navigation-compose:2.10.0-rc01")

    testImplementation(kotlin("test"))
}

application {
    mainClass.set("MainKt")
}

// Disable distribution tasks that have duplicate file issues with Compose 1.12.x
tasks.named("distTar").configure { enabled = false }
tasks.named("distZip").configure { enabled = false }

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

tasks.withType<JavaCompile>().configureEach {
    targetCompatibility = "21"
    sourceCompatibility = "21"
}