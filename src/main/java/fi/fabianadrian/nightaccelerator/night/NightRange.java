package fi.fabianadrian.nightaccelerator.night;

public enum NightRange {
	CLEAR(12523, 23477),
	RAIN(12002, 23998);

	public final long start;
	public final long end;
	public final long duration;

	NightRange(long start, long end) {
		this.start = start;
		this.end = end;
		this.duration = end - start;
	}

	private boolean isInRange(long time) {
		return time >= this.start && time <= this.end;
	}

	public float progress(long time) {
		if (!isInRange(time)) {
			return 0f;
		}

		return (float) (time - this.start) / this.duration;
	}
}
