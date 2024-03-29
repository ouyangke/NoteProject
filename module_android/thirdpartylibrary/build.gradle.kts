plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.oyke.thirdpartylibrary"
    compileSdk = 33

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.getName())
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    //网络请求库
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")
    //图片加载库
    implementation("com.github.bumptech.glide:glide:4.15.1")
    //内存泄露检测库
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.11")
    //KV组件
    implementation("com.tencent:mmkv:1.2.16")
    //路由库(用于组件化改造)
    api("com.alibaba:arouter-api:1.5.2")
    annotationProcessor("com.alibaba:arouter-compiler:1.5.2")
    //动画库
    implementation("com.airbnb.android:lottie:6.0.0")
    //蓝牙库
    implementation("com.github.Jasonchenlijian:FastBle:2.4.0")
}