pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(uri("repo"))
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(uri("repo"))
    }
}

rootProject.name = "Sword"
include(":app")
include(":api-kotlin")
include(":kcp:plugin-gradle")
include(":kcp:plugin-kotlin")
include(":compiler:ksp")
