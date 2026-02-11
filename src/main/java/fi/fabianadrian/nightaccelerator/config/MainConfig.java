package fi.fabianadrian.nightaccelerator.config;

import fi.fabianadrian.nightaccelerator.config.section.BossbarSection;
import fi.fabianadrian.nightaccelerator.config.section.CurveSection;
import fi.fabianadrian.nightaccelerator.config.section.TitleSection;
import space.arim.dazzleconf.engine.liaison.SubSection;

import java.util.List;

public interface MainConfig {
	default List<String> enabledWorlds() {
		return List.of("world");
	}

	@SubSection
	TitleSection title();

	@SubSection
	BossbarSection bossbar();

	@SubSection
	CurveSection curve();
}
