import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.build.config)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))

dependencies {
    compileOnly(libs.kotlin.compiler.embeddable)

    kapt(libs.google.auto.service)
    compileOnly(libs.google.auto.service.annotations)

    testImplementation(project(":api-kotlin"))
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.kotlin.compile.testing)
}

buildConfig {
    packageName("com.guodong.android.sword.kcp.kotlin")
    buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["KOTLIN_PLUGIN_ID"]}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${rootProject.extra["VERSION_NAME"]}\"")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf(
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