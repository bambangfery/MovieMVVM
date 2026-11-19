plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.bambang.moviemvvm"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.bambang.moviemvvm"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.androidx.swiperefreshlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    //Retrofit and GSON
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.jetbrains.kotlinx.coroutines.core)

    //Kodein Dependency Injection
    implementation(libs.kodein.di.generic.jvm)
    implementation(libs.kodein.di.framework.android.x)

    implementation(libs.picasso)
    implementation(libs.core)
}