import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    application
    id("idea")
}

group = "com.facuramallo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = VERSION_17


sourceSets{
    create("integrationTests"){
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val integrationTests = task<Test>("integrationTests") {
    description = "Runs integration tests."
    group = "com.facuramallo"

    testClassesDirs = sourceSets["integrationTests"].output.classesDirs
    classpath = sourceSets["integrationTests"].runtimeClasspath

    shouldRunAfter("test")
}

val integrationTestsImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
    extendsFrom(configurations.testImplementation.get())
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

configurations["integrationTestsRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())
configurations["integrationTestsRuntimeOnly"].extendsFrom(configurations.testRuntimeOnly.get())



tasks.check { dependsOn(integrationTests) }


repositories {
    mavenCentral()
}

dependencies {
    // Spring dependencies
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("net.logstash.logback:logstash-logback-encoder:7.4")

    // Test
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("io.rest-assured:spring-mock-mvc:5.3.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events(
            PASSED,
            SKIPPED,
            FAILED
        )
        exceptionFormat = FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}

tasks.named<BootBuildImage>("bootBuildImage") {
    imageName = "logging_example_app"
    environment.apply {
        put("BP_JVM_VERSION","17")
        put("spring.profiles.active","k8s")
    }
}

application {
    mainClass.set("com.facuramallo.logging.LoggingApplicationKt")
}