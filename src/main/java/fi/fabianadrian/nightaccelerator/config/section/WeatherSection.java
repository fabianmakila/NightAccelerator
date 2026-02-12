package fi.fabianadrian.nightaccelerator.config.section;

import space.arim.dazzleconf.engine.Comments;

public interface WeatherSection {
	@Comments("Should weather be cleared in the morning")
	default boolean clearEnabled() {
		return true;
	}

	default int clearMin() {
		return 12000;
	}

	default int clearMax() {
		return 180000;
	}
}
