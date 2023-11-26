plugins {
    alias(libs.plugins.kotlin.jvm)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))

publishing {
    repositories {
        maven {
            name = "Local"
            url = rootProject.uri("repo")
        }
    }
}