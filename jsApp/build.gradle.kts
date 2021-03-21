
plugins {
    kotlin("js")
}

dependencies {
    implementation(project(":shared"))
}

group = "org.kobjects.komponents"


kotlin {

    js(LEGACY) {
        browser {
            binaries.executable()
            webpackTask {
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
            }
        }
    }


}