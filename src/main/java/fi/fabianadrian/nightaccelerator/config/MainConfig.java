package fi.fabianadrian.nightaccelerator.config;

import fi.fabianadrian.nightaccelerator.config.section.AccelerationSection;
import fi.fabianadrian.nightaccelerator.config.section.DisplaySection;
import fi.fabianadrian.nightaccelerator.config.section.MorningSection;
import space.arim.dazzleconf.engine.liaison.SubSection;

import java.util.List;

public interface MainConfig {
	default List<String> enabledWorlds() {
		return List.of("world");
	}

	@SubSection
	DisplaySection display();

	@SubSection
	AccelerationSection acceleration();

	@SubSection
	MorningSection morning();
}
