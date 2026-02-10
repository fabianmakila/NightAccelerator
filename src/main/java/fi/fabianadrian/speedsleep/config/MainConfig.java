package fi.fabianadrian.speedsleep.config;

import fi.fabianadrian.speedsleep.config.section.BossbarSection;
import fi.fabianadrian.speedsleep.config.section.CurveSection;
import fi.fabianadrian.speedsleep.config.section.TitleSection;
import space.arim.dazzleconf.engine.liaison.SubSection;

import java.util.List;

public interface MainConfig {
	default List<String> disabledWorldNames() {
		return List.of();
	}

	@SubSection
	TitleSection title();

	@SubSection
	BossbarSection bossbar();

	@SubSection
	CurveSection curve();
}
