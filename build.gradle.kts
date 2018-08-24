import org.gradle.internal.impldep.org.apache.maven.model.Build
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

project.apply { from("./versions.gradle.kts") }

val kotlinVrs: String by extra
val kotlinxCoroutinesVrs: String by extra
val ardulinkVrs: String by extra
val tornadoFxVrs: String by extra

plugins {
    java
    kotlin("jvm") version "1.2.60-eap-7"
    application
}

group = "com.jamdan2.ServoTrajectoryGui"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-eap") }
}

dependencies {
    kotlin("").toString()
    compile(kotlin("stdlib-jdk8", kotlinVrs))
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", kotlinxCoroutinesVrs)
    compile("org.ardulink", "ardulink-core-base", ardulinkVrs)
    compile("org.ardulink", "ardulink-core-serial-jssc", ardulinkVrs)
    compile("no.tornado", "tornadofx", tornadoFxVrs)
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = project.name
    manifest {
        attributes["Main-Class"] = "com.company.servotrajectorygui.MainKt"
    }
    from(configurations.runtime.map {
        @Suppress("IMPLICIT_CAST_TO_ANY")
        if (it.isDirectory) it else zipTree(it)
    })
    with(tasks["jar"] as CopySpec)
}

val distribute = task("distribute", type = Copy::class) {
    from("build/libs") {
        include("*.jar")
    }
    from(projectDir) {
        include("README.md")
    }
    into("build/distributions/${project.name}-${project.version}")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    "build" {
        dependsOn(fatJar)
    }

    task("buildAndDistribute") {
        dependsOn("build")
        dependsOn(distribute)
    }
}

kotlin {
    experimental {
        coroutines = Coroutines.ENABLE
    }
}

application {
    mainClassName = "com.company.servotrajectorygui.MainKt"
}
