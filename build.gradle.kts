import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml.Load

plugins {
	id("java")
	id("xyz.jpenilla.resource-factory-paper-convention") version "1.3.1"
	id("com.diffplug.spotless") version "8.2.1"
	id("com.gradleup.shadow") version "9.3.1"
}

group = "fi.fabianadrian"
version = "2.0.0"
description = "Night go wrooom"

repositories {
	mavenCentral()
	maven("https://repo.papermc.io/repository/maven-public/")
	maven("https://repo.helpch.at/releases/") // PlaceholderAPI
	maven("https://eldonexus.de/repository/maven-public/") // StrokkCommands
	maven("https://repo.faststats.dev/releases") // FastStats
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(25))

tasks {
	compileJava {
		options.encoding = Charsets.UTF_8.name()
	}
	build {
		dependsOn(spotlessApply, shadowJar)
	}
	shadowJar {
		minimize()
	}
}

dependencies {
	compileOnly("io.papermc.paper:paper-api:26.1.2.build.+")

	// Placeholders
	compileOnly("io.github.miniplaceholders:miniplaceholders-api:3.2.0")
	compileOnly("me.clip:placeholderapi:2.12.2")

	// Commands
	compileOnly("net.strokkur.commands:annotations-paper:2.1.4")
	annotationProcessor("net.strokkur.commands:processor-paper:2.1.4")

	// Misc
	implementation("space.arim.dazzleconf:dazzleconf-toml:2.0.0-M2")
	implementation("dev.faststats.metrics:bukkit:0.27.1")
}

paperPluginYaml {
	main = "fi.fabianadrian.nightaccelerator.NightAccelerator"
	author = "FabianAdrian"
	apiVersion = "26.1"
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

