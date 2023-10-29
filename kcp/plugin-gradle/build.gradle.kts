import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-gradle-plugin")
    kotlin("jvm")
    id("com.github.gmazzo.buildconfig")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
}

buildConfig {
    packageName("com.guodong.android.sword.kcp.gradle")
    buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["KOTLIN_PLUGIN_ID"]}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"com.guodong.android\"")
    buildConfigField("String", "KOTLIN_PLUGIN_NAME", "\"sword-kcp-kotlin-plugin\"")
    buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${rootProject.extra["PLUGIN_VERSION"]}\"")
}

gradlePlugin {
    plugins {
        create("Sword") {
            id = rootProject.extra["KOTLIN_PLUGIN_ID"] as String
            displayName = "Sword Kcp"
            description = "Sword Kcp"
            implementationClass = "com.guodong.android.sword.kcp.gradle.SwordGradlePlugin"
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    repositories {
        maven {
            name = "Local"
            url = rootProject.uri("repo")
        }
    }
}