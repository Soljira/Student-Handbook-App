plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.map.secret)

    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0"
}

android {
    namespace = "com.example.studenthandbookapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.studenthandbookapp"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.google.maps)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.material:material:1.13.0-alpha09")

    implementation("com.redmadrobot:input-mask-android:7.2.4")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin")

    // Firebase stuff
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-analytics")  // note: remove this later if hindi na need based sa requirements ni sir
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")

    // Jetpack Compose stuff
    // Specify the Compose BOM with a version definition
    val composeBom = platform("androidx.compose:compose-bom:2025.01.01")
    implementation(composeBom)
    implementation("androidx.activity:activity-compose")
    //This dependency includes the fundamental UI elements and features provided by Jetpack Compose.
    implementation("androidx.compose.ui:ui")
    //This includes tooling and preview functionalities for Compose, assisting with development and debugging UI components.
    implementation("androidx.compose.ui:ui-tooling-preview")
    //This dependency includes the Material Design 3 components and styles adapted for Jetpack Compose, allowing the implementation of Material Design principles in your app's UI
    implementation("androidx.compose.material3:material3")
    debugImplementation(libs.androidx.ui.tooling)
//    implementation("androidx.compose.compiler")
}