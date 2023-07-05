@file:Suppress("UNUSED_VARIABLE")

import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalDistributionDsl

plugins {
    kotlin("multiplatform") version "1.8.22"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

kotlin {
    // kotlin multiplatform (jvm + js) setup:
    jvm {
        jvmToolchain(11)
    }
    js(IR) {
        binaries.executable()
        browser {
            @OptIn(ExperimentalDistributionDsl::class)
            distribution {
                directory = File("${rootDir}/dist/js")
            }
        }
    }
    
    sourceSets {
        // Choose your kool version:
        val koolVersion = "0.11.0"              // latest stable version
        //val koolVersion = "0.12.1-SNAPSHOT"   // newer but minor breaking changes might occur from time to time

        // JVM target platforms, you can remove entries from the list in case you want to target
        // only a specific platform
        val targetPlatforms = listOf("natives-windows", "natives-linux", "natives-macos")

        val commonMain by getting {
            dependencies {
                // add additional kotlin multi-platform dependencies here...

                implementation("de.fabmax.kool:kool-core:$koolVersion")
                implementation("de.fabmax.kool:kool-physics:$koolVersion")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
            }
        }

        val jvmMain by getting {
            dependencies {
                // add additional jvm-specific dependencies here...

                // add required runtime libraries for lwjgl and physx-jni
                for (platform in targetPlatforms) {
                    // lwjgl runtime libs
                    val lwjglVersion = "3.3.2"
                    listOf("glfw", "opengl", "jemalloc", "nfd", "stb", "vma", "shaderc").forEach { lib ->
                        runtimeOnly("org.lwjgl:lwjgl-$lib:$lwjglVersion:$platform")
                    }
                    runtimeOnly("org.lwjgl:lwjgl:$lwjglVersion:$platform")

                    // physx-jni runtime libs - these have to match the physx-jni version used by kool-physics...
                    val physxJniVersion = when (koolVersion) {
                        "0.11.0" -> "2.0.5"
                        else -> "2.2.1"
                    }
                    runtimeOnly("de.fabmax:physx-jni:$physxJniVersion:$platform")
                }
            }
        }
        
        val jsMain by getting {
            dependencies {
                // add additional js-specific dependencies here...
            }
        }
    }
}

task("runnableJar", Jar::class) {
    dependsOn("jvmJar")

    group = "app"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveAppendix.set("runnable")
    manifest {
        attributes["Main-Class"] = "LauncherKt"
    }

    val jvmConfig = configurations.getByName("jvmRuntimeClasspath").copyRecursive()
    from(
        jvmConfig.fileCollection { true }.files.map { if (it.isDirectory) it else zipTree(it) },
        files("$buildDir/classes/kotlin/jvm/main")
    )

    doLast {
        copy {
            from("$buildDir/libs/${archiveBaseName.get()}-runnable.jar")
            into("${rootDir}/dist/jvm")
        }
    }
}

task("launch", JavaExec::class) {
    group = "app"
    dependsOn("jvmMainClasses")

    val jvmConfig = configurations.getByName("jvmRuntimeClasspath").copyRecursive()
    classpath = jvmConfig.fileCollection { true } + files("$buildDir/classes/kotlin/jvm/main")
    mainClass.set("LauncherKt")
}

val build by tasks.getting(Task::class) {
    dependsOn("runnableJar")
}

val clean by tasks.getting(Task::class) {
    doLast {
        delete("${rootDir}/dist")
    }
}
