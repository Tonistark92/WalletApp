plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.iscoding.designsystem"
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

    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.androidx.material3)
    val composeBom =
        platform(
            "androidx.compose:compose-bom:2026.08.00"
        )

    implementation(composeBom)

    androidTestImplementation(composeBom)


    implementation(
        libs.androidx.compose.runtime
    )

    implementation(
        libs.androidx.ui
    )

    implementation(
        libs.androidx.compose.foundation
    )

    implementation(
        libs.androidx.ui.tooling.preview
    )


    implementation(
        libs.androidx.material.icons.extended
    )


    debugImplementation(
        libs.androidx.ui.tooling
    )
}