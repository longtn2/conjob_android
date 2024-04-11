plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.4.30"
    id("kotlin-parcelize")
    kotlin("kapt")
}

val appName = "AppName"

android {
    namespace = "com.intern.conjob"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.intern.conjob"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs{
        getByName("debug") {
            storeFile = file("../keystores/debug.jks")
        }

        create("production") {
            storeFile = file("/keystores/release.jks")
            storePassword = ""
            keyAlias = ""
            keyPassword = ""
        }
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("production")
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
        }

        create("staging") {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".staging"
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    applicationVariants.all {
        when (name) {
            "debug" -> {
                resValue("string", "app_name", "[DEV]-${appName}")
                buildConfigField("String", "API_URL", "\"https://api.coingecko.com/api/v3/\"")
            }

            "staging" -> {
                resValue("string", "app_name", "[STG]-${appName}")
                buildConfigField("String", "API_URL", "\"https://api.coingecko.com/api/v3/\"")
            }

            "release" -> {
                resValue("string", "app_name", appName)
                buildConfigField("String", "API_URL", "\"https://api.coingecko.com/api/v3/\"")
            }
        }
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.11.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    testImplementation ("junit:junit:4.13.2")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1"
    )
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("com.github.bumptech.glide:glide:4.13.0")
    kapt("com.github.bumptech.glide:compiler:4.13.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
}