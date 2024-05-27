plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.sword)
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "com.guodong.android.sword.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.guodong.android.sword.app"
        minSdk = 21
        targetSdk = 34
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
            signingConfig = signingConfigs["release"]
        }
        getByName("release") {
            signingConfig = signingConfigs["release"]
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = true
    }

    kotlinOptions {
        jvmTarget = "1.8"

        // Use K2
        // languageVersion = "2.0"
    }

    // https://kotlinlang.org/docs/ksp-quickstart.html#make-ide-aware-of-generated-code
    // Generated source files are registered automatically since KSP 1.8.0-1.0.9.
    /*sourceSets {
        named("debug") {
            java.srcDirs("build/generated/ksp/debug/kotlin")
        }
        named("release") {
            java.srcDirs("build/generated/ksp/release/kotlin")
        }
    }*/
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