package fi.fabianadrian.nightaccelerator.config.section;

import space.arim.dazzleconf.engine.Comments;
import space.arim.dazzleconf.engine.liaison.IntegerRange;

public interface AccelerationSection {
	@Comments("Interval in ticks on how frequently should time be added")
	@Comments("Smaller values offer smoother experience at the cost of performance")
	@IntegerRange(min = 1, max = 20)
	default int updateRate() {
		return 2;
	}

	@IntegerRange(min = 0)
	@Comments("The minimum amount of ticks to skip with each update (one player sleeps)")
	default int min() {
		return 0;
	}

	@Comments("The maximum amount of tick to skip with each update (all players sleep)")
	default int max() {
		return 40;
	}

	@Comments("Modify this to change the shape of the curve")
	@Comments("A factor of 1.0 equals a linear curve")
	default double factor() {
		return 1.0;
	}

	@Comments("Available options:")
	@Comments("NONE - Weather won't be accelerated")
	@Comments("RAIN - Weather will be accelerated if it's raining or thundering")
	@Comments("ALWAYS - Weather will be accelerated with the time")
	default AccelerateWeather accelerateWeather() {
		return AccelerateWeather.NONE;
	}

	enum AccelerateWeather {
		NONE, RAIN, ALWAYS
	}
}