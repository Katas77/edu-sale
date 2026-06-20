plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose) // Добавлен плагин для поддержки Compose в Kotlin 2.x
}

android {
    namespace = "com.example.common" // Сохраняем имя пакета вашего общего модуля
    compileSdk = 37 // Исправлено: стандартный синтаксис компиляции под стабильный SDK 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    // ИСПРАВЛЕНО: Включен Jetpack Compose, чтобы в common можно было хранить общие UI-компоненты
    buildFeatures {
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

    // ИСПРАВЛЕНО: Поднято до Java 17, так как AGP 9.1.1 и Kotlin 2.2.10 требуют эту версию
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // ИСПРАВЛЕНО: Добавлены библиотеки Jetpack Compose через 'api', чтобы делиться ими с другими модулями
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material3)

    // ИСПРАВЛЕНО: Добавлен Koin через 'api' для сквозного доступа во всем проекте
    api(libs.koin.android)
    api(libs.koin.androidx.compose)

    // Тесты
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
