val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.3.40"
}

group = "com.viartemev"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("ch.qos.logback:logback-classic:$logback_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("io.ktor:ktor-jackson:$ktor_version")
    runtime("org.postgresql:postgresql:42.1.4")
    compile("javax.annotation:javax.annotation-api:1.3.2")
    compile("org.jooq:jooq:3.11.11")
    compile("org.flywaydb:flyway-core:5.2.4")
    compile("com.zaxxer:HikariCP:2.7.4")
    testCompile("io.ktor:ktor-server-tests:$ktor_version")
}

apply(from = "useJooq.gradle")

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
