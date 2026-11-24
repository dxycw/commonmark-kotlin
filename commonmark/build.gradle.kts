import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.androidLint)

    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.google.osdetector") version "1.7.3" // desktop的生成标准化分类器

    id("com.vanniktech.maven.publish") version "0.37.0" // 使用最新稳定版

}

kotlin {

    android {
        namespace = "org.commonmark"
        compileSdk {
            version = release(37){
                minorApiLevel = 1 // 指定次要 API 级别为 1，即 37.1
            }
        }
        minSdk = 24

        // 从 Android Gradle 插件版本 8.8.0 开始
        // 必须显式开启，否则 androidMain/res 被忽略
        androidResources {
            enable = true
        }

//        aarMetadata {
//            minCompileSdk = 37  // 但这对上游依赖无效
//        }

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        withHostTestBuilder {}

        withDeviceTestBuilder { sourceSetTreeName = "test" }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    listOf(
//        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "commonmarkKit"
            isStatic = true
        }
    }

    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                // Add KMP dependencies here
                compileOnly(libs.compose.runtime)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                // Add Android-specific dependencies here. Note that this source set depends on
                // commonMain by default and will correctly pull the Android artifacts of any KMP
                // dependencies declared in commonMain.
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.core)
                implementation(libs.androidx.testExt.junit)
                implementation(libs.androidx.testRunner)
            }
        }

        iosMain {
            dependencies {
                // Add iOS-specific dependencies here. This a source set created by Kotlin Gradle
                // Plugin (KGP) that each specific iOS target (e.g., iosX64) depends on as
                // part of KMP’s default source set hierarchy. Note that this source set depends
                // on common by default and will correctly pull the iOS artifacts of any
                // KMP dependencies declared in commonMain.
            }
        }
    }

}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}

mavenPublishing {
    // 指向 Central Portal（不是旧的 OSSRH）
    publishToMavenCentral()

    // 自动签名所有 publication
    signAllPublications()

    coordinates(
        groupId = "io.github.dxycw",
        artifactId = "commonmark",
        version = "1.0.0" // 从 gradle.properties 或版本目录读取更佳
    )

    pom {
        name.set("commonmark-kotlin")
        description.set("用于Kotlin多平台项目（Android、iOS、Desktop (Windows, MacOS 和 Linux)和 Web）的“commonmark-kotlin”包，使用Jetpack Compose Multiplatform开发。 注意：本库是本作者个人私库（不公开分享本库，不建议他人使用），如果想使用本库可以在项目中使用依赖库或克隆分支（可以自己新建一个分支修改本库，不可提交到本库），请不要上传提交，请勿私自外传本项目。")
        url.set("https://github.com/dxycw/commonmark-kotlin")

        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0")
            }
        }
        developers {
            developer {
                id.set("dxycw")
                name.set("commonmark-kotlin")
                email.set("3293666408@qq.com")
            }
        }
        scm {
            connection.set("scm:git:git://github.com/dxycw/commonmark-kotlin.git")
            developerConnection.set("scm:git:ssh://github.com/dxycw/commonmark-kotlin.git")
            url.set("https://github.com/dxycw/commonmark-kotlin")
        }
    }
}