plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.sword)
    alias(libs.plugins.google.ksp)
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.guodong.android.sword.app"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("guodongAndroid.jks")
            storePassword = "33919135"
            keyAlias = "guodongandroid"
            keyPassword = "33919135"
        }
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("boolean", "isDebug", "true")
            signingConfig = signingConfigs["release"]
        }
        getByName("release") {
            buildConfigField("boolean", "isDebug", "false")
            signingConfig = signingConfigs["release"]
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        named("debug") {
            java.srcDirs("build/generated/ksp/debug/kotlin")
        }
        named("release") {
            java.srcDirs("build/generated/ksp/release/kotlin")
        }
    }
}

ksp {
    arg("sword.pkg", "com.guodong.android.sword.app")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)

    debugImplementation(project(":api-kotlin"))
    releaseImplementation(libs.sword.api.kotlin)

    kspDebug(project(":compiler:ksp"))
    kspRelease(libs.sword.compiler.ksp)
}