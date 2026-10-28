import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.4.21"
    id("org.jetbrains.compose") version "1.12.2"
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.21"
    application
}

group = "com.vibe.kotlin101"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.compose.material3:material3:1.13.0-beta01")
    implementation("org.jetbrains.compose.foundation:foundation:1.12.2")
    implementation("org.jetbrains.compose.ui:ui:1.12.2")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.8.0")

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