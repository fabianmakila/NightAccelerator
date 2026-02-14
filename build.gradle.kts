import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml.Load

plugins {
	id("java")
	id("xyz.jpenilla.resource-factory-paper-convention") version "1.3.1"
	id("com.diffplug.spotless") version "8.2.1"
	id("com.gradleup.shadow") version "9.3.1"
}

group = "fi.fabianadrian"
version = "1.0.0"
description = "Night go wrooom"

repositories {
	mavenCentral()
	maven("https://repo.papermc.io/repository/maven-public/")
	maven("https://repo.helpch.at/releases/") // PlaceholderAPI
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

tasks {
	compileJava {
		options.encoding = Charsets.UTF_8.name()
	}
	build {
		dependsOn(spotlessApply, shadowJar)
	}
	shadowJar {
		sequenceOf(
			"org.bstats",
			"space.arim.dazzleconf"
		).forEach { pkg ->
			relocate(pkg, "fi.fabianadrian.nightaccelerator.dependency.$pkg")
		}
	}
}

dependencies {
	compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
	compileOnly("io.github.miniplaceholders:miniplaceholders-api:3.1.0")
	compileOnly("me.clip:placeholderapi:2.12.2")
	implementation("space.arim.dazzleconf:dazzleconf-toml:2.0.0-M2")
	implementation("org.bstats:bstats-bukkit:3.1.0")
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
			register("PlaceholderAPI") {
				required = false
				load = Load.BEFORE
			}
		}
	}
}

