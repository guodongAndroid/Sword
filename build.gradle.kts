// Top-level build file where you can add configuration options common to all sub-projects/modules.
/*buildscript {
    repositories {
        google()
        mavenCentral()
        maven(rootProject.uri("repo"))
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath(kotlin("gradle-plugin", "1.7.10"))

        // release
//        classpath("com.guodong.android:sword-gradle-plugin:${plugin_version}")
//        classpath("com.guodong.android:sword-kcp-gradle-plugin:${plugin_version}")

        // debug
//        classpath("com.guodong.android:sword-gradle-plugin:${project.PLUGIN_VERSION}")
//        classpath("com.guodong.android:sword-kcp-gradle-plugin:${project.extra["PLUGIN_VERSION"]}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}*/

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
    delete(rootProject.buildDir)
}