plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
//    //KOIN
    implementation(libs.koin.core)


    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.gson)
    implementation(libs.json)
    implementation(libs.okhttp.v4120)
    implementation(libs.retrofit.v2110)



}
