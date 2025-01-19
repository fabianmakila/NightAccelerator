plugins {
    id("java")
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.2.0"
}

group = "fi.fabianadrian"
version = "1.0.0-SNAPSHOT"
description = "Night go wrooom"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    implementation("org.spongepowered:configurate-yaml:4.0.0")
}

paperPluginYaml {
    main = "fi.fabianadrian.speedsleep.SpeedSleep"
    author = "FabianAdrian"
    apiVersion = "1.20"
}

