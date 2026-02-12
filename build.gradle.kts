import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml.Load

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
	compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
	implementation("space.arim.dazzleconf:dazzleconf-toml:2.0.0-M2")
	compileOnly("io.github.miniplaceholders:miniplaceholders-api:3.1.0")
}

paperPluginYaml {
	main = "fi.fabianadrian.nightaccelerator.NightAccelerator"
	author = "FabianAdrian"
	apiVersion = "1.21.11"
	dependencies {
		server {
			register("MiniPlaceholders") {
				required = false
				load = Load.BEFORE
			}
		}
	}
}

