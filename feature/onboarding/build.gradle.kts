plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.iscoding.onboarding"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // --------------------------------
    // Compose
    // --------------------------------

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.compose.ui.tooling.preview)


    // --------------------------------
    // Lifecycle / ViewModel
    // --------------------------------

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)


    // --------------------------------
    // Coroutines
    // --------------------------------

    implementation(libs.kotlinx.coroutines.android)


    // --------------------------------
    // Navigation
    // --------------------------------

    // Add this only if onboarding owns navigation destinations.
    // implementation(libs.androidx.navigation.compose)


    // --------------------------------
    // Design System
    // --------------------------------

    implementation(project(":designsystem"))
    implementation(project(":core:common"))



    // --------------------------------
    // Debug / Preview
    // --------------------------------

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)


    // --------------------------------
    // Unit Tests
    // --------------------------------

    testImplementation(libs.junit)


    // --------------------------------
    // Compose UI Tests
    // --------------------------------

    androidTestImplementation(platform(libs.androidx.compose.bom))

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}