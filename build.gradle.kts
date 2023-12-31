import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    alias(libs.plugins.about.libraries)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint.gradle)
}

group = "com.github.kitakkun.mirrorcomment"
version = "1.0-SNAPSHOT"

val appName = "MirrorComment"
val appVersion = "1.0.0"
val generatedSrcDir = "${project.buildDir.path}/generated/source/kotlin"

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
            kotlin.srcDir(generatedSrcDir)
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
                implementation(libs.about.libraries.core)
                implementation(libs.about.libraries.compose)

                implementation(libs.multiplatform.settings)
                implementation(libs.multiplatform.settings.test)
                // need this to resolve Dispatcher.Main for Desktop
                implementation(libs.coroutines.swing)
            }
        }
        val jvmTest by getting
    }
}

dependencies {
    commonTestImplementation(kotlin("test"))
    commonTestImplementation(libs.koin.test)
}

compose.desktop {
    application {
        mainClass = "com.github.kitakkun.mirrorcomment.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = appName
            packageVersion = appVersion
            // ref: https://stackoverflow.com/questions/61727613/unexpected-behaviour-from-gson/74914488#74914488
            modules("jdk.unsupported")
            macOS {
                iconFile.set(project.file("icon.icns"))
            }
            linux {
                iconFile.set(project.file("icon.png"))
            }
            windows {
                iconFile.set(project.file("icon.ico"))
            }
        }
        buildTypes.release {
            proguard {
                configurationFiles.from("proguard-rules.pro")
            }
        }
    }
}

aboutLibraries {
    registerAndroidTasks = false
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.from("detekt.yml")
    source.from("src/jvmMain/kotlin")
    ignoreFailures = true
}

ktlint {
    version.set(libs.versions.ktlint.asProvider())
    reporters {
        reporter(ReporterType.CHECKSTYLE)
    }
    ignoreFailures.set(true)
    verbose.set(true)
}

// generate BuildConfig class to access application meta-data from source code.
task("generateBuildConfigClass") {
    doLast {
        val content = """
            package ${project.group}
            
            object BuildConfig {
                const val VERSION_NAME = "$appVersion"
                const val APP_NAME = "$appName"
            }
        """.trimIndent()

        val file = file("$generatedSrcDir/${project.group.toString().replace(".", "/")}/BuildConfig.kt")
        file.parentFile.mkdirs()
        file.writeText(content)
    }
}

tasks.withType<KotlinCompile>().configureEach {
    dependsOn("generateBuildConfigClass")
}
