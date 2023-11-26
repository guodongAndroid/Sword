import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))

dependencies {
    implementation(libs.google.ksp.api)
    implementation(libs.kotlin.poet)
    implementation(libs.kotlin.poet.ksp)
    implementation(project(":api-kotlin"))

    testImplementation(project(":api-kotlin"))
    testImplementation(kotlin("test-junit"))
    testImplementation(libs.kotlin.compile.testing)
    testImplementation(libs.kotlin.compile.testing.ksp)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-opt-in=com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview",
            "-opt-in=com.google.devtools.ksp.KspExperimental",
            "-opt-in=org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi",
        )
    }
}

publishing {
    repositories {
        maven {
            name = "Local"
            url = rootProject.uri("repo")
        }
    }
}