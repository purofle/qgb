plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
}
val ktorClientVersion: String by rootProject.extra
fun ktor(id: String, version: String = this@Build_gradle.ktorClientVersion) = "io.ktor:ktor-client-$id:$version"

kotlin {
    dependencies {
        api(ktor("core"))
        api(ktor("okhttp"))
        api(ktor("serialization"))
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

repositories {
    mavenCentral()
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}