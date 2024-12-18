plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt") // Necesario para la inyección de dependencias con Hilt


}

android {
    namespace = "com.heidygonzalez.applibros"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.heidygonzalez.applibros"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.play.services.basement)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit y otras dependencias
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("com.squareup.okhttp3:okhttp:4.9.0") // Reemplaza con la versión que uses

    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3") // Usa esta versión o la más reciente. agregado uno nuevo


    implementation ("androidx.compose.material3:material3:1.0.0") //material3 agregado

    implementation("io.coil-kt:coil-compose:2.3.0")



    implementation(kotlin("script-runtime"))

    //se agrega esto de momento

    implementation ("androidx.compose.material3:material3:1.0.0") // Asegúrate de usar la última versión
    implementation ("androidx.compose.ui:ui:1.4.0") // O la versión más reciente
    implementation ("androidx.compose.material:material-icons-extended:1.4.0")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")

    // se agrega mas

    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")  // Para usar Hilt con Compose
    implementation ("com.google.dagger:hilt-android:2.44")          // Dependencia principal de Hilt
    kapt ("com.google.dagger:hilt-compiler:2.44")                // Para usar Hilt con anotaciones
}