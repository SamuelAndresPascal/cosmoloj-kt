import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
    application
}

repositories {
    mavenCentral()
}

subprojects {

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("io.gitlab.arturbosch.detekt")
    }

    repositories {
        mavenCentral()
    }

    group = "com.cosmoloj.kt"
    version = "1.0-SNAPSHOT"

    dependencies {
        testImplementation(kotlin("test"))
    }

    tasks.test {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_22)
        }
    }

    detekt {
        buildUponDefaultConfig = true // preconfigure defaults
        allRules = false // activate all available (even unstable) rules.
        config.setFrom("$rootDir/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
        // baseline = file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = JavaVersion.VERSION_21.toString()
        reports {
            html.required.set(true) // observe findings in your browser with structure and code snippets
            xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
            txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
            sarif.required.set(true) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with GitHub Code Scanning
            md.required.set(true) // simple Markdown format
        }
    }
}

application {
    mainClass.set("MainKt")
}
