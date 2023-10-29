// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(uri("repo"))

        maven("https://raw.githubusercontent.com/guodongAndroid/maven/main/repository/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath(kotlin("gradle-plugin", "1.7.10"))

        // release
//        classpath("com.guodong.android:sword-gradle-plugin:${plugin_version}")
//        classpath("com.guodong.android:sword-kcp-gradle-plugin:${plugin_version}")

        // debug
//        classpath("com.guodong.android:sword-gradle-plugin:${project.PLUGIN_VERSION}")
        classpath("com.guodong.android:sword-kcp-gradle-plugin:${project.extra["PLUGIN_VERSION"]}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    kotlin("jvm") version "1.7.10" apply false
    id("com.google.devtools.ksp") version "1.7.10-1.0.6" apply false
    id("com.github.gmazzo.buildconfig") version "3.1.0" apply false
    id("com.vanniktech.maven.publish") version "0.22.0" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        flatDir {
            dirs("repo")
        }
        maven(uri(rootProject.uri("repo")))

        maven("https://raw.githubusercontent.com/guodongAndroid/maven/main/repository/")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}