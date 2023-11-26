plugins {
    alias(libs.plugins.gradle.plugin.publish)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.build.config)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))

dependencies {
    implementation(kotlin("gradle-plugin-api"))
}

buildConfig {
    packageName("com.guodong.android.sword.kcp.gradle")
    buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["KOTLIN_PLUGIN_ID"]}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"com.sunxiaodou.kotlin\"")
    buildConfigField("String", "KOTLIN_PLUGIN_NAME", "\"sword-kcp-kotlin-plugin\"")
    buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${rootProject.extra["VERSION_NAME"]}\"")
}

gradlePlugin {
    plugins {
        vcsUrl.set("https://github.com/guodongAndroid/Sword")
        website.set(vcsUrl)

        create("Sword") {
            id = rootProject.extra["KOTLIN_PLUGIN_ID"] as String
            displayName = "Sword Kcp"
            description = "Sword Kcp"
            implementationClass = "com.guodong.android.sword.kcp.gradle.SwordGradlePlugin"
            tags.addAll("sword", "proxy", "kotlin", "kcp")
        }
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