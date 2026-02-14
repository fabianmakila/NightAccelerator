package fi.fabianadrian.nightaccelerator.config.section;

import space.arim.dazzleconf.engine.Comments;
import space.arim.dazzleconf.engine.liaison.FloatRange;

public interface MorningSection {
	@Comments("Whether weather should be cleared in the morning")
	default boolean clearWeather() {
		return true;
	}

	@Comments("Minimum clear weather time in ticks")
	default int clearMin() {
		return 12000;
	}

	@Comments("Maximum clear weather time in ticks")
	default int clearMax() {
		return 180000;
	}

	@Comments("Sound to play when a player wakes up")
	@Comments("Leave empty to disable")
	default String sound() {
		return "minecraft:entity.player.levelup";
	}

	@Comments("Volume of the wake-up sound")
	@FloatRange(min = 0)
	default float soundVolume() {
		return 1;
	}

	@Comments("Pitch of the wake-up sound")
	@FloatRange(min = 0, max = 2)
	default float soundPitch() {
		return 1;
	}
}
