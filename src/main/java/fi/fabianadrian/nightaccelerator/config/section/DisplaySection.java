package fi.fabianadrian.nightaccelerator.config.section;

import space.arim.dazzleconf.engine.Comments;
import space.arim.dazzleconf.engine.liaison.SubSection;

public interface DisplaySection {
	@Comments("Interval in milliseconds how frequently bossbar and titles will be updated")
	default int updateRate() {
		return 500;
	}

	@SubSection
	TitleSection title();

	@SubSection
	BossbarSection bossbar();
}
