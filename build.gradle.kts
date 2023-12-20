// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.build.config) apply false
    alias(libs.plugins.maven.publish) apply false
    alias(libs.plugins.sword) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(rootProject.uri("repo"))
    }
}

subprojects {
    if (name != "app") {
        apply(plugin = "com.vanniktech.maven.publish")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}