import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.Property

plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("application")
    id("jacoco")
//    id("io.gitlab.arturbosch.detekt") version "1.23.4"
//    id("org.jlleitschuh.gradle.ktlint") version "12.0.3"
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

val starterVersion = "3.2.0"
val jooqPluginVersion = "8.2.1"
val kotlinVersion = "1.9.20"
val sqliteVersion = "3.44.1.0"
val flywayVersion = "9.21.1"
val jooqApiVersion = "3.19.0"
val jacksonVersion = "2.14.2"
val slf4jVersion = "2.0.9"
val securityTestVersion = "6.0.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq:$starterVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$starterVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$starterVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$starterVersion")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:$starterVersion")

    jooqGenerator("org.xerial:sqlite-jdbc:$sqliteVersion")
    jooqGenerator("org.slf4j:slf4j-jdk14:$slf4jVersion")
    jooqGenerator("org.jooq:jooq-meta-extensions:$jooqApiVersion")

    runtimeOnly("org.xerial:sqlite-jdbc:$sqliteVersion")
    api("org.jooq:jooq-codegen:$jooqApiVersion")
    implementation("org.xerial:sqlite-jdbc:$sqliteVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
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
val dbSchema = "main"
val dbDriver = "org.sqlite.JDBC"

jooq {
    version.set(jooqApiVersion)
    configurations {
        create("main") {
            jooqConfiguration.apply {
                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                    database.apply {
                        name = "org.jooq.meta.extensions.ddl.DDLDatabase"
                        excludes = "flyway_schema_history"
                        properties.addAll(
                            listOf(
                                Property().withKey("scripts")
                                    .withValue("src/main/resources/db/migration/*.sql"),
                                Property().withKey("sort").withValue("semantic"),
                                Property().withKey("unqualifiedSchema").withValue("none"),
                                Property().withKey("defaultNameCase").withValue("as_is")
                            )
                        )
                        // inputSchema = "public"
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

//jooq {
//    version.set(jooqApiVersion)
//    configurations {
//        create("main") {
//            jooqConfiguration.apply {
//                jdbc.apply {
//                    driver = dbDriver
//                    url = dbUrl
//                    user = dbUser
//                    password = dbPassword
//                }
//                generator.apply {
//                    name = "org.jooq.codegen.KotlinGenerator"
//                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
//                    database.apply {
//                        name = "org.jooq.meta.sqlite.SQLiteDatabase"
//                        excludes = "flyway_schema_history"
//                        // inputSchema = "public"
//                    }
//                    generate.apply {
//                        isDeprecated = false
//                        isRecords = true
//                        isImmutablePojos = false
//                        isFluentSetters = true
//                        isJavaBeansGettersAndSetters = false
//                        isSerializablePojos = true
//                        isVarargSetters = false
//                        isPojos = true
//                        isUdts = false
//                        isRoutines = false
//                        isIndexes = false
//                        isRelations = true
//                        isPojosEqualsAndHashCode = true
//                        isJavaTimeTypes = true
//                    }
//                    target.apply {
//                        packageName = "sidim.doma.undying.generated"
//                        directory = "build/generated-sources/jooq"
//                    }
//                }
//            }
//        }
//    }
//}

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