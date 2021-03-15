import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
    kotlin("jvm") version "1.4.31"
}

group = "kws"
version = "1.0-SNAPSHOT"

repositories {
    repositories {
        mavenCentral()
        jcenter()
    }
}

dependencies {
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.4.3")
    
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

tasks.withType<KotlinJvmCompile>().configureEach {

    kotlinOptions {
        jvmTarget = "1.8"
        suppressWarnings = true
        apiVersion = "1.4"
        languageVersion = "1.4"
        freeCompilerArgs += "-Xopt-in=kotlin.contracts.ExperimentalContracts"
    }
}





