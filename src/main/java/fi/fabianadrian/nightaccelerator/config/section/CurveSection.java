package fi.fabianadrian.nightaccelerator.config.section;

public interface CurveSection {
	default int min() {
		return 0;
	}

	default int max() {
		return 20;
	}

	default double factor() {
		return 1.0;
	}
}
