plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
//    id("com.guodong.android.sword.kcp")
    id("com.sunxiaodou.kotlin.sword.kcp")
    id("com.google.devtools.ksp")
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

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    debugImplementation(project(":api-kotlin"))
    releaseImplementation("com.sunxiaodou.kotlin:sword-api-kt:${project.extra["PLUGIN_VERSION"]}")

    kspDebug(project(":compiler:ksp"))
    kspRelease("com.sunxiaodou.kotlin:sword-compiler-ksp:${project.extra["PLUGIN_VERSION"]}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}