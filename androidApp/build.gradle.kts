plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    `maven-publish`
}

group = "org.kobjects.komponents"


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.stefanhaustein.komponents"
            artifactId = "androidApp"
        }
    }
}


dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.2")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "org.kobjects.komponents.androidApp"
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
