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
	default int min() {
		return 0;
	}

	default int max() {
		return 40;
	}

	default double factor() {
		return 1.0;
	}
}
