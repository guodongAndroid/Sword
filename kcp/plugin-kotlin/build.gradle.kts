import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("com.github.gmazzo.buildconfig")
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")

    kapt("com.google.auto.service:auto-service:1.0.1")
    compileOnly("com.google.auto.service:auto-service-annotations:1.0.1")
}

buildConfig {
    packageName("com.guodong.android.sword.kcp.kotlin")
    buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["KOTLIN_PLUGIN_ID"]}\"")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.register("sourcesJar", Jar::class) {
    group = "build"
    description = "Assembles Kotlin sources"

    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
    dependsOn(tasks.classes)
}

publishing {
    publications {
        create<MavenPublication>("default") {
            groupId = rootProject.extra["GROUP_ID"].toString()
            artifactId = project.extra["ARTIFACT_ID"].toString()
            version = rootProject.extra["PLUGIN_VERSION"].toString()
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }

    repositories {
        maven {
            name = "Local"
            url = rootProject.uri("repo")
        }

        maven {

            // https://github.com/guodongAndroid/maven.git
            val mavenUrl = uri(getMavenUrl())

            name = "Github"
            url = mavenUrl
        }
    }
}

fun getMavenUrl(): String {
    val properties = Properties()
    val dis = rootProject.file("local.properties").inputStream()
    properties.load(dis)
    return properties.getProperty("maven.url")
}