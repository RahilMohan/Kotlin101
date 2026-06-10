import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.3.21"
    application
}

group = "com.vibe.kotlin101"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("com.github.ajalt.mordant:mordant:2.7.2")
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("MainKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

tasks.withType<JavaCompile>().configureEach {
    targetCompatibility = "21"
    sourceCompatibility = "21"
}
