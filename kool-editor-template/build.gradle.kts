import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalDistributionDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("multiplatform") version "2.0.0"
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

kotlin {
    jvm {
        compilations.create("editor")
    }
    jvmToolchain(11)

    js {
        binaries.executable()
        browser {
            @OptIn(ExperimentalDistributionDsl::class)
            distribution {
                outputDirectory.set(File("${projectDir}/dist/js"))
            }
            commonWebpackConfig {
                //mode = KotlinWebpackConfig.Mode.PRODUCTION
                mode = KotlinWebpackConfig.Mode.DEVELOPMENT
            }
        }
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            target.set("es2015")
        }
    }

    sourceSets {
        val koolVersion = "0.16.0-SNAPSHOT"
        val lwjglVersion = "3.3.3"
        val physxJniVersion = "2.4.0"

        // JVM target platforms, you can remove entries from the list in case you want to target
        // only a specific platform
        val targetPlatforms = listOf("natives-windows", "natives-linux", "natives-macos", "natives-macos-arm64")

        val commonMain by getting {
            dependencies {
                implementation("de.fabmax.kool:kool-editor-model:$koolVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                // add additional jvm-specific dependencies here...

                // add required runtime libraries for lwjgl and physx-jni
                for (platform in targetPlatforms) {
                    // lwjgl runtime libs
                    runtimeOnly("org.lwjgl:lwjgl:$lwjglVersion:$platform")
                    listOf("glfw", "opengl", "jemalloc", "nfd", "stb", "vma", "shaderc").forEach { lib ->
                        runtimeOnly("org.lwjgl:lwjgl-$lib:$lwjglVersion:$platform")
                    }

                    // physx-jni runtime libs
                    runtimeOnly("de.fabmax:physx-jni:$physxJniVersion:$platform")
                }
            }
        }

        val jvmEditor by getting {
            dependencies {
                implementation("de.fabmax.kool:kool-editor:$koolVersion")
            }
        }

        val jsMain by getting {
            dependencies {
                // add additional js-specific dependencies here...

                // editor dependency should only be included if js editor project is build
                implementation("de.fabmax.kool:kool-editor:$koolVersion")
            }
        }

        sourceSets.all {
            languageSettings.apply {
                progressiveMode = true
            }
        }
    }
}

configurations.filter { "editor" in it.name }.forEach {
    // editor related configurations need some custom attribute to distinguish them from regular jvm configs
    it.attributes.attribute(Attribute.of("de.fabmax.kool-editor", String::class.java), "editor")
}

tasks["clean"].doLast {
    delete("${projectDir}/dist/js")
}

task("runEditor", JavaExec::class) {
    group = "editor"
    dependsOn("jvmEditorClasses")

    classpath = layout.buildDirectory.files("classes/kotlin/jvm/editor")
    configurations
        .filter { it.name.startsWith("common") || it.name.startsWith("jvm") }
        .map { it.copyRecursive().fileCollection { true } }
        .forEach { classpath += it }

    mainClass.set("EditorLauncherKt")
    workingDir = File(projectDir, ".editor")
    if (!workingDir.exists()) {
        workingDir.mkdir()
    }
}

task("runApp", JavaExec::class) {
    group = "app"
    dependsOn("jvmMainClasses")

    classpath = layout.buildDirectory.files("classes/kotlin/jvm/main")
    configurations
        .filter { it.name.startsWith("common") || it.name.startsWith("jvm") }
        .map { it.copyRecursive().fileCollection { true } }
        .forEach { classpath += it }

    mainClass.set("de.fabmax.kool.app.AppLauncherKt")
}
