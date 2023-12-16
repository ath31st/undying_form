rootProject.name = "undying"

pluginManagement {

    plugins {
        val kotlinVersion = "1.9.20"
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.allopen") version kotlinVersion

        id("io.gitlab.arturbosch.detekt") version "1.23.4"
        id("nu.studer.jooq") version "8.2.1"
        id("org.jlleitschuh.gradle.ktlint") version "12.0.3"
        id("org.flywaydb.flyway") version "9.21.1"
    }
}