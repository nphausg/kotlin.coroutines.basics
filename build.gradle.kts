plugins {
    kotlin("jvm") version "1.8.0"
}

group = "sg.nphau.kotlin.coroutines"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    // coroutine
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
