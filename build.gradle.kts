buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:11.8.0")
    }
}

apply(plugin = "org.flywaydb.flyway")

plugins {
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("application")
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    id("org.jlleitschuh.gradle.ktlint") version "12.2.0"
    id("org.jetbrains.kotlin.plugin.allopen") version "2.1.20"
    id("nu.studer.jooq") version "10.1"
    id("org.flywaydb.flyway") version "11.8.0"
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.spring") version "2.1.20"
}

allOpen {
    annotation("sidim.doma.OpenClass")
}

group = "sidim.doma"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

kotlin {
    jvmToolchain(21)
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

val starterVersion: String by project
val kotlinVersion: String by project
val flywayVersion: String by project
val jooqApiVersion: String by project
val jacksonVersion: String by project
val slf4jVersion: String by project
val securityTestVersion: String by project
val postgresVersion: String by project
val springdocVersion: String by project
val opencsvVersion: String by project
val modelMapperVersion: String by project
val kotlinLogging: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq:$starterVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$starterVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$starterVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$starterVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:$starterVersion")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:$starterVersion")

    runtimeOnly("org.postgresql:postgresql:$postgresVersion")
    jooqGenerator("org.postgresql:postgresql:$postgresVersion")
    jooqGenerator("org.slf4j:slf4j-jdk14:$slf4jVersion")
    jooqGenerator("org.jooq:jooq-meta-extensions:$jooqApiVersion")
    api("org.jooq:jooq-codegen:$jooqApiVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.flywaydb:flyway-database-postgresql:$flywayVersion")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-starter-common:$springdocVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("com.opencsv:opencsv:$opencsvVersion")
    implementation("org.modelmapper:modelmapper:$modelMapperVersion")
    implementation("io.github.oshai:kotlin-logging:${kotlinLogging}")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$starterVersion")
    testImplementation("org.springframework.security:spring-security-test:$securityTestVersion")
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

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        xml.outputLocation.set(File("build/reports/jacoco/report.xml"))
        html.required.set(true)
        csv.required.set(false)
    }
    classDirectories.setFrom(
        fileTree("build/classes/kotlin/main") {}
    )
}

tasks.jacocoTestCoverageVerification {
    classDirectories.setFrom(
        fileTree("build/classes/kotlin/main") {}
    )
    violationRules {
        rule {
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                //minimum = BigDecimal.valueOf(0.9)
                minimum = BigDecimal.valueOf(0.1)
            }
        }
    }
}

//tasks.named("check") {
//    dependsOn("detektMain")
//    dependsOn("detektTest")
//    dependsOn("jacocoTestCoverageVerification")
//}

detekt {
    files(
        "src/main/kotlin",
        "src/test/kotlin"
    )
}

ktlint {
    setOf("no-wildcard-imports")
}

tasks.named("build") {
    dependsOn("detekt")
}

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

val dbUrl = "jdbc:postgresql://localhost:5444/" + (System.getenv("PG_DB") ?: "dev_undying_db")
val dbUser = System.getenv("PG_USER") ?: "dev_user"
val dbPassword = System.getenv("DB_PASSWORD") ?: "dev_password"
val dbSchema = "public"
val dbDriver = "org.postgresql.Driver"

jooq {
    version.set(jooqApiVersion)
    configurations {
        create("main") {
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
                        inputSchema = dbSchema
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = false
                        isFluentSetters = true
                        isJavaBeansGettersAndSetters = false
                        isSerializablePojos = true
                        isVarargSetters = false
                        isPojos = true
                        isUdts = false
                        isRoutines = false
                        isIndexes = false
                        isRelations = true
                        isPojosEqualsAndHashCode = true
                        isJavaTimeTypes = true
                    }
                    target.apply {
                        packageName = "sidim.doma.undying.generated"
                        directory = "build/generated-sources/jooq"
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
    mixed = true
}

extensions.findByName("buildScan")?.withGroovyBuilder {
    setProperty("termsOfServiceUrl", "https://gradle.com/terms-of-service")
    setProperty("termsOfServiceAgree", "no")
}