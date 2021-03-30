import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlin-android-extensions")
    `maven-publish`
}

group = "org.kobjects.komponents"

version = "0.1.0"

kotlin {
    android()
    ios() /* {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }*/
    js {
        browser {
          /*  webpackTask {
                cssSupport.enabled = true
            }

            runTask {
                cssSupport.enabled = true
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }*/
        }
    //    binaries.executable()
    }
    cocoapods() {
        summary = "Komponents Kotlin native UI library"
        homepage = "https://github.com/stefanhaustein/komponents"
        ios.deploymentTarget = "13.5"

        pod ("SwiftSVG") {
            version = "~> 2.0"
        }

//        pod ("CocoaLumberjack")
   /*     pod("SVGKit") {
       //     version = "~> 2.1.1"
        }

            source = git("https://github.com/SVGKit/SVGKit.git") {
                branch = "3.x"
            }
        }*/
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.2.1")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13")
            }
        }
        val iosMain by getting
        val iosTest by getting
        val jsMain by getting
    }
}

android {
    compileSdkVersion(29)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(29)
    }

    dependencies {
        implementation("com.caverock:androidsvg-aar:1.4")
    }
}



val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)



afterEvaluate {
    publishing {
        publications {
            val release by publications.registering(MavenPublication::class) {
                from(components["release"])
                artifactId = "shared"
                groupId = "com.github.stefanhaustein.komponents"
            }
        }
    }
}