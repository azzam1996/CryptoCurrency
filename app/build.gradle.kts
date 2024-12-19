plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.junit5)
}

android {
    namespace = "com.azzam.cryptocurrency"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.azzam.cryptocurrency"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "URL",
            "\"https://api.coincap.io/v2/\""
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
    implementation(libs.gson)
    implementation(libs.coil)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(platform(libs.okhttp))
    implementation(libs.logging.interceptor)
    implementation(libs.constraintlayout)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation)

    //Testing Libs
    testImplementation (libs.mockwebserver)
    testImplementation (libs.okhttp3.okhttp)
    testImplementation (libs.truth)
    testImplementation (libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation (libs.mockk)
    testImplementation (libs.androidx.core.testing)
    testImplementation(libs.junit.jupiter)

    // Android Testing Libs
    androidTestImplementation (libs.truth)
    androidTestImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testRuntimeOnly (libs.junit.jupiter.engine)
}