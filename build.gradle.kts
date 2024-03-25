import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("application")
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.4"
    id("org.jlleitschuh.gradle.ktlint") version "12.0.3"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.20"
    id("nu.studer.jooq") version "8.2.1"
    id("org.flywaydb.flyway") version "9.21.1"
    kotlin("jvm") version "1.9.21"
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

val starterVersion = "3.2.4"
val jooqPluginVersion = "8.2.1"
val kotlinVersion = "1.9.23"
val flywayVersion = "9.21.1"
val jooqApiVersion = "3.19.3"
val jacksonVersion = "2.16.1"
val slf4jVersion = "2.0.9"
val securityTestVersion = "6.1.5"
val postgresVersion = "42.7.1"
val springdocVersion = "2.3.0"
val opencsvVersion = "5.9"
val modelMapperVersion = "3.2.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq:$starterVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$starterVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$starterVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$starterVersion")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:$starterVersion")

    jooqGenerator("org.postgresql:postgresql:$postgresVersion")
    jooqGenerator("org.slf4j:slf4j-jdk14:$slf4jVersion")
    jooqGenerator("org.jooq:jooq-meta-extensions:$jooqApiVersion")

    runtimeOnly("org.postgresql:postgresql:$postgresVersion")
    api("org.jooq:jooq-codegen:$jooqApiVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-starter-common:$springdocVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("com.opencsv:opencsv:$opencsvVersion")
    implementation("org.modelmapper:modelmapper:$modelMapperVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$starterVersion")
    testImplementation("org.springframework.security:spring-security-test:$securityTestVersion")
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
}

extensions.findByName("buildScan")?.withGroovyBuilder {
    setProperty("termsOfServiceUrl", "https://gradle.com/terms-of-service")
    setProperty("termsOfServiceAgree", "no")
}