import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.text.SimpleDateFormat

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.baseproject"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.baseproject"
        minSdk = 29
        targetSdk = 37
//        versionCode = 100
//        versionName = "1.0.0"

        versionCode = 1
        versionName = "test"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    androidComponents {
        onVariants(selector().withBuildType("release")) { variant ->
            val dateTime = SimpleDateFormat("dd-MM-yyyy")
                .format(System.currentTimeMillis())

            variant.outputs.forEach { output ->
                val versionCode = output.versionCode.get()
                output.outputFileName.set(
                    "Magic_Gesture_(${versionCode})_$dateTime.apk"
                )
            }
        }
    }

    signingConfigs {
        create("release") {

            // Tự động tìm file có đuôi .jks hoặc .keystore đầu tiên trong thư mục app
            val keystoreFile = project.file(".")
                .listFiles()
                ?.firstOrNull { it.name.endsWith(".jks") || it.name.endsWith(".keystore") }
                ?: file("release.jks") // File dự phòng nếu không tìm thấy

            // Tự động nhận diện file jks nằm cùng thư mục
            storeFile = keystoreFile

            // Gọi mật khẩu an toàn từ biến Group của GitLab
            storePassword = System.getenv("COMPANY_KEY_PASSWORD") ?: ""
            keyPassword = System.getenv("COMPANY_KEY_PASSWORD") ?: ""

            keyAlias = "key0"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Áp dụng cấu hình release vừa thiết lập
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin.compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }

    buildFeatures {
        viewBinding = true
    }

    bundle {
        language {
            enableSplit = false
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //ads library
    implementation(libs.ssquadadslibrary)

    //other library
    implementation(libs.lottie)
    implementation(libs.glide)
    implementation(libs.user.messaging.platform)

    implementation(libs.gson)
    implementation(libs.play.services.ads)


}