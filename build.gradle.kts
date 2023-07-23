import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.github.kitakkun.mirrorcomment"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.selenium)
                implementation(libs.koin.core)

                // kt-vox
                implementation(libs.ktvox)
                implementation(libs.retrofit)

                // voyager
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.bottom.sheet.navigator)
                implementation(libs.voyager.tab.navigator)
                implementation(libs.voyager.transitions)

                implementation(libs.material.icons.extended)
                implementation(libs.webdrivermanager)

                implementation(libs.compose.color.picker)
                implementation(libs.compose.color.picker.jvm)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "com.github.kitakkun.mirrorcomment.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MirrorComment"
            packageVersion = "1.0.0"
        }
    }
}
