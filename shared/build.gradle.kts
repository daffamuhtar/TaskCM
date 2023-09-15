plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.squareup.sqldelight")
//    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "1.8.21"
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:0.16.1")
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }


    val coroutinesVersion = "1.6.4"
    val ktorVersion = "2.2.1"
    val koinVersion = "3.3.2"


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

//                implementation("app.cash.sqldelight:runtime:2.0.0")
                implementation("com.squareup.sqldelight:runtime:1.5.5")
                implementation("com.squareup.sqldelight:coroutines-extensions:1.5.5")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

                //ktor
                implementation("media.kamel:kamel-image:0.6.0")
//                implementation("io.ktor:ktor-client-cio:2.2.1")
                implementation("io.ktor:ktor-client-auth:2.1.0")
                implementation("io.ktor:ktor-client-logging:2.2.1")
                implementation("io.ktor:ktor-client-core:2.3.1")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.1")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

//                implementation("androidx.collection:collection:1.3.0-dev01")
//
//                // Lower-level APIs with support for custom serialization
//                implementation("androidx.datastore:datastore-core-okio:1.1.0-dev01")
//                // Higher-level APIs for storing values of basic types
//                implementation("androidx.datastore:datastore-preferences-core:1.1.0-dev01")
//
//                implementation("de.gal-digital:kmm-preferences:0.0.1")
                implementation("com.google.code.gson:gson:2.9.0")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
//                implementation("app.cash.sqldelight:android-driver:2.0.0")
                implementation("com.squareup.sqldelight:android-driver:1.5.5")
                implementation("androidx.appcompat:appcompat:1.6.1")
                implementation("androidx.activity:activity-compose:1.7.2")

                //ktor
//                api("androidx.activity:activity-compose:1.6.1")
//                api("androidx.appcompat:appcompat:1.6.1")
//                api("androidx.core:core-ktx:1.9.0")
                implementation("io.ktor:ktor-client-cio:2.2.1")
                implementation("io.ktor:ktor-client-android:2.3.1")
                implementation("io.ktor:ktor-client-auth:2.1.0")
                implementation("io.ktor:ktor-client-logging:2.2.1")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                implementation("app.cash.sqldelight:native-driver:2.0.0")
                implementation("com.squareup.sqldelight:native-driver:1.5.5")
                implementation("io.ktor:ktor-client-darwin:2.3.1")
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.daffamuhtar.taskcm"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    database("ContactDatabase") {
        packageName = "com.daffamuhtar.taskcm.database"
        sourceFolders = listOf("sqldelight")
    }
}
//sqldelight {
//    databases {
//        create("Database") {
//            packageName.set("com.example")
//        }
//    }
//}
dependencies {
    implementation("androidx.core:core:1.10.1")
    commonMainApi("dev.icerock.moko:mvvm-core:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-compose:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow-compose:0.16.1")
}
