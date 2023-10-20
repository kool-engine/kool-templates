import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalDistributionDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("multiplatform") version "1.9.10"
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

kotlin {
    jvm {
        jvmToolchain(11)
        compilations.create("editor")
    }

    js(IR) {
        binaries.executable()
        browser {
            @OptIn(ExperimentalDistributionDsl::class)
            distribution(Action {
                outputDirectory.set(File("${projectDir}/dist/js"))
            })
            commonWebpackConfig(Action {
                //mode = KotlinWebpackConfig.Mode.PRODUCTION
                mode = KotlinWebpackConfig.Mode.DEVELOPMENT
            })
        }
    }

    sourceSets {
        // Choose your kool version:
        val koolVersion = "0.12.1"              // latest stable version
        //val koolVersion = "0.13.0-SNAPSHOT"   // newer but minor breaking changes might occur from time to time

        val lwjglVersion = "3.3.3"
        val physxJniVersion = "2.3.1"

        // JVM target platforms, you can remove entries from the list in case you want to target
        // only a specific platform
        val targetPlatforms = listOf("natives-windows", "natives-linux", "natives-macos")

        val commonMain by getting {
            dependencies {
                implementation("de.fabmax.kool:kool-core:$koolVersion")
                implementation("de.fabmax.kool:kool-physics:$koolVersion")
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

tasks["jsBrowserDistribution"].doLast {
    // create asset browser index json
    val baseDir = File("$projectDir/src/commonMain/resources")
    File("${projectDir}/dist/js/available-assets.json").writer().use { outWriter ->
        val assetPaths = mutableListOf<String>()
        fileTree(baseDir).visit {
            if (path.startsWith("assets")) {
                assetPaths.add(path)
            }
        }
        outWriter.appendLine("[")
        outWriter.append(assetPaths.joinToString(separator = ",\n") { "  \"${it.replace('\\', '/')}\"" })
        outWriter.append("\n]")
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

    val editorConfig = configurations.getByName("jvmEditorRuntimeClasspath").copyRecursive()

    classpath = editorConfig.fileCollection { true } + files("$projectDir/build/classes/kotlin/jvm/editor")
    mainClass.set("EditorLauncherKt")
    workingDir = File(projectDir, ".editor")

    if (!workingDir.exists()) {
        workingDir.mkdir()
    }
}