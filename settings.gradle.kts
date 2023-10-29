pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "Sword"
include(":app")
include(":api-kotlin")
include(":kcp:plugin-gradle")
include(":kcp:plugin-kotlin")
include(":compiler:ksp")
