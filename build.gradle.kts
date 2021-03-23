buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
        classpath("com.android.tools.build:gradle:4.2.0-beta06")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
    }
}

group = "org.kobjects.komponents"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}
