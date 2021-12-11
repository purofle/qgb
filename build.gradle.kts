plugins {
    kotlin("jvm") version "1.5.10"
    id("org.jetbrains.dokka") version "1.6.0"
}

buildscript {

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.5.10")
    }
}

allprojects {
    group = "xyz.cuya"

    repositories {
        mavenCentral()
    }
}