plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {

    sourceSets {
        named("main") {
            res.srcDirs("./res")
            manifest.srcFile("./AndroidManifest.xml")
        kotlin.srcDirs("./kotlin")
    }
    }
/*dexOptions {
javaMaxHeapSize = "4G"
}*/
    namespace = "kz.aisuluait"
    compileSdk = 34

    defaultConfig {
        applicationId = "kz.aisuluait.screen.comments"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
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
    buildFeatures {
//        compose = true
    }
/*    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }*/
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
implementation("androidx.core:core:1.12.0")
}
