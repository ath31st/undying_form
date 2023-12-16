import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("nu.studer:gradle-jooq-plugin:8.2.1")
    }
}
apply(plugin = "nu.studer.jooq")

//configurations.all {
//    resolutionStrategy.eachDependency {
//        if (requested.group == "org.jetbrains.kotlin") {
//            useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
//        }
//    }
//}

plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("application")
    id("jacoco")
//    id("io.gitlab.arturbosch.detekt") version "1.23.4"
    id("org.jlleitschuh.gradle.ktlint") version "12.0.3"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.20"
    id("nu.studer.jooq") version "8.2.1"
    id("org.flywaydb.flyway") version "9.21.1"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
}

allOpen {
    annotation("sidim.doma.OpenClass")
}

group = "sidim.doma"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

val jooqPluginVersion = "8.2.1"
val kotlinVersion = "1.9.20"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq:3.2.0")
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.0")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("org.flywaydb:flyway-core:9.21.1")
    api ("org.jooq:jooq-codegen:3.19.0")
    implementation("nu.studer:gradle-jooq-plugin:$jooqPluginVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.xerial:sqlite-jdbc:3.44.1.0")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.2.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.0")
    testImplementation("org.springframework.security:spring-security-test:6.0.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("sidim.doma.undying.UndyingFormApplicationKt")
}

jacoco {
    toolVersion = "0.8.8"
}

//tasks.jacocoTestReport {
//    reports {
//        xml.required.set(true)
//        xml.outputLocation.set(File("$buildDir/reports/jacoco/report.xml"))
//        html.required.set(true)
//        csv.required.set(false)
//    }
//    classDirectories.setFrom(
//        fileTree("build/classes/kotlin/main") {}
//    )
//}
//
//tasks.jacocoTestCoverageVerification {
//    classDirectories.setFrom(
//        fileTree("build/classes/kotlin/main") {}
//    )
//    violationRules {
//        rule {
//            limit {
//                counter = "INSTRUCTION"
//                value = "COVEREDRATIO"
//                //minimum = BigDecimal.valueOf(0.9)
//                minimum = BigDecimal.valueOf(0.1)
//            }
//        }
//    }
//}

//tasks.named("check") {
//    dependsOn("detektMain")
//    dependsOn("detektTest")
//    dependsOn("jacocoTestCoverageVerification")
//}

//detekt {
//    files(
//        "src/main/kotlin",
//        "src/test/kotlin"
//    )
//}

//ktlint {
//    disabledRules.set(setOf("no-wildcard-imports"))
//}

//tasks.named("build") {
//    dependsOn("detekt")
//}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources"))
        archiveClassifier.set("standalone")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) }
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }
    build {
        dependsOn(fatJar)
    }
}

val dbUrl = "jdbc:sqlite:./undying_form.db"
val dbUser = "admin"
val dbPassword = System.getenv("DB_PASSWORD") ?: "admin"
val dbSchema = "default_schema"
val dbDriver = "org.sqlite.JDBC"

jooq {
    version.set("3.19.0")
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration.apply {
                jdbc.apply {
                    driver = dbDriver
                    url = dbUrl
                    user = dbUser
                    password = dbPassword
                }
                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        excludes = "flyway_schema_history"
                        inputSchema = "public"
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isFluentSetters = true
                        isRelations = true
                        isImmutablePojos = true
                        // isKotlinNotNullRecordAttributes = true
                    }
                    target.apply {
                        packageName = "sidim.doma.undying.generated"
                    }
                }
            }
        }
    }
}

flyway {
    cleanDisabled = false
    url = dbUrl
    user = dbUser
    password = dbPassword
    schemas = arrayOf(dbSchema)
    driver = dbDriver
}