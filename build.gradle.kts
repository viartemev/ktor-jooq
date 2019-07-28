import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.dsl.Coroutines

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    java
    application
    kotlin("jvm") version "1.3.40"
    id("com.github.johnrengelman.shadow") version "5.1.0"
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
    compile("io.ktor:ktor-locations:$ktor_version")
    compile("ch.qos.logback:logback-classic:$logback_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("io.ktor:ktor-jackson:$ktor_version")
    runtime("org.postgresql:postgresql:42.1.4")
    compile("javax.annotation:javax.annotation-api:1.3.2")
    compile("org.flywaydb:flyway-core:5.2.4")
    compile("com.zaxxer:HikariCP:2.7.4")
    testCompile("io.ktor:ktor-server-tests:$ktor_version")

    /** JOOQ **/
    compile("org.jooq:jooq:3.11.11")
    //compile("org.jooq:jooq-meta:3.11.11")
    //compile("org.jooq:jooq-codegen:3.11.11")
}

apply(from = "useJooq.gradle")
kotlin.experimental.coroutines = Coroutines.ENABLE

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("joo-ktor")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to application.mainClassName))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

/*
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClassName
    }
    from(configurations.runtime.get().map { if (it.isDirectory) it else zipTree(it) })
}*/
