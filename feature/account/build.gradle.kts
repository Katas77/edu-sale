plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose) // Добавлен плагин для поддержки Compose в Kotlin 2.x
}

android {
    namespace = "com.example.account" // Приведено к правильному имени пакета вашего модуля
    compileSdk = 37 // Исправлено: стандартный синтаксис компиляции под стабильный SDK 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    // ИСПРАВЛЕНО: Обязательный блок для включения Jetpack Compose в модуле
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

    // ИСПРАВЛЕНО: Добавлены библиотеки Jetpack Compose для этого модуля
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3) // Дает доступ к кнопкам, текстам и т.д.

    // Тесты
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
