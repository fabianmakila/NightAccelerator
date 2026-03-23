package fi.fabianadrian.nightaccelerator.night;

public enum SleepWindow {
	CLEAR(12542, 23480),
	RAIN(12010, 0);

	public final long start;
	public final long end;

	SleepWindow(long start, long end) {
		this.start = start;
		this.end = end;
	}
}
