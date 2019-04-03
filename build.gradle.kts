import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.irotsoma.web"
version = "0.1-SNAPSHOT"

val kotlinLoggingVersion = "1.6.22"
val javaxValidationVersion = "2.0.1.Final"
val passayVersion = "1.4.0"

plugins {
    kotlin("jvm") version "1.3.21"
    id("org.jetbrains.dokka") version "0.9.18"
}

val repoUsername = project.findProperty("ossrhUsername") ?: System.getenv("ossrhUsername") ?: ""
val repoPassword = project.findProperty("ossrhPassword") ?: System.getenv("ossrhPassword") ?: ""

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("javax.validation:validation-api:$javaxValidationVersion")
    implementation("org.passay:passay:$passayVersion")
    implementation("commons-beanutils:commons-beanutils:1.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.1")
    testImplementation("org.hibernate.validator:hibernate-validator:6.0.16.Final")
    testImplementation("org.glassfish:javax.el:3.0.1-b09")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}