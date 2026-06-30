package fi.fabianadrian.nightaccelerator.config;

import fi.fabianadrian.nightaccelerator.config.section.AccelerationSection;
import fi.fabianadrian.nightaccelerator.config.section.BossbarSection;
import fi.fabianadrian.nightaccelerator.config.section.MorningSection;
import fi.fabianadrian.nightaccelerator.config.section.TitleSection;
import space.arim.dazzleconf.engine.liaison.SubSection;

import java.util.List;
import java.util.Locale;

public interface MainConfig {
	default List<String> enabledWorlds() {
		return List.of("world");
	}

	default Locale defaultLocale() {
		return Locale.getDefault();
	}

	@SubSection
	TitleSection title();

	@SubSection
	BossbarSection bossbar();

	@SubSection
	AccelerationSection acceleration();

	@SubSection
	MorningSection morning();
}
