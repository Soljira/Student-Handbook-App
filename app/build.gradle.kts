plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.map.secret)

    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0"
    id("kotlin-kapt")  // For annotation processing
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

    sourceSets {
        getByName("main") {
            assets.srcDirs("src/main/assets")
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation ("androidx.credentials:credentials:1.2.0")
    implementation ("androidx.credentials:credentials-play-services-auth:1.2.0")
    implementation ("com.google.android.libraries.identity.googleid:googleid:1.1.0")
    // Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // If you need to use Glide's annotation processor (for generated API)
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.material:material:1.13.0-alpha09")

    implementation("com.redmadrobot:input-mask-android:7.2.4")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin")

    // Firebase stuff
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-analytics")  // note: remove this later if hindi na need based sa requirements ni sir
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.firebase:firebase-appcheck-ktx")
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

//    implementation("com.google.gms:google-services:4.4.2")
//    implementation("com.google.android.gms:play-services-base")

    // Room components
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")


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
    debugImplementation(libs.androidx.ui.test.manifest)
//    implementation("androidx.compose.compiler")
}