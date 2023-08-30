plugins {
    id("com.android.application")
    id("org.jetbrains.compose")
    id("com.google.gms.google-services")
    kotlin("android")
}

android {
    namespace = "com.daffamuhtar.taskcm.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.daffamuhtar.taskcm.android"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.10"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.activity:activity-compose:1.7.1")

    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
//    implementation("com.google.firebase:firebase-analytics-ktx:21.3.0.")
    implementation("com.google.firebase:firebase-messaging-ktx:23.2.1")
//
//    //Preference DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
}