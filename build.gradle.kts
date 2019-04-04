import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.irotsoma.web"
version = "0.1-SNAPSHOT"

val kotlinLoggingVersion = "1.6.22"
val javaxValidationVersion = "2.0.1.Final"
val passayVersion = "1.4.0"
val apacheBeanUtilsVersion = "1.9.3"
val apacheCommonsLangVersion = "3.8.1"

plugins {
    kotlin("jvm") version "1.3.21"
    id("org.jetbrains.dokka") version "0.9.18"
    signing
    `maven-publish`
}

val repoUsername = project.findProperty("ossrhUsername")?.toString() ?: System.getenv("ossrhUsername") ?: ""
val repoPassword = project.findProperty("ossrhPassword")?.toString() ?: System.getenv("ossrhPassword") ?: ""

repositories {
    mavenCentral()
    jcenter()
    gradlePluginPortal()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("javax.validation:validation-api:$javaxValidationVersion")
    implementation("org.passay:passay:$passayVersion")
    implementation("commons-beanutils:commons-beanutils:$apacheBeanUtilsVersion")
    implementation("org.apache.commons:commons-lang3:$apacheCommonsLangVersion")
    //test dependencies
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

extra["isReleaseVersion"] = !version.toString().endsWith("SNAPSHOT")


val dokka by tasks.getting(DokkaTask::class) {
    outputDirectory = "$buildDir/docs/javadoc"
    jdkVersion = 8
    reportUndocumented = true
}

defaultTasks("dokka")

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.register<Jar>("javadocJar"){
    archiveClassifier.set("javadoc")
    from(dokka.outputDirectory)
}

publishing {
    publications {
        create<MavenPublication>("mavenKotlin"){
            artifactId = project.name
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
            pom {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()
                name.set("Web Validation")
                description.set("Web validation library using Annotations.")
                url.set("https://irotsoma.github.io/web_validation/")
                licenses{
                    license{
                        name.set("GNU Lesser General Public License, Version 3.0")
                        url.set("http://www.gnu.org/licenses/lgpl.txt")
                        distribution.set("repo")
                    }
                }
                developers{
                    developer{
                        id.set("irotsoma")
                        name.set("Justin Zak")
                        email.set("irotsomadev@gmail.com")
                        organization.set("Irotsoma, LLC")
                        organizationUrl.set("https://www.irotsoma.com")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:irotsoma/cloudbackenc.git")
                    developerConnection.set("scm:git:git@github.com:irotsoma/cloudbackenc.git")
                    url.set("git@github.com:irotsoma/cloudbackenc.git")
                }
            }
            repositories {
                maven {
                    val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                    val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots")
                    url = if (project.extra["isReleaseVersion"] as Boolean) releasesRepoUrl else snapshotsRepoUrl
                    credentials{
                        username = repoUsername
                        password = repoPassword
                    }
                }
            }
        }
    }
}

signing {
    setRequired({
        (project.extra["isReleaseVersion"] as Boolean)
    })
    sign(publishing.publications["mavenKotlin"])
}