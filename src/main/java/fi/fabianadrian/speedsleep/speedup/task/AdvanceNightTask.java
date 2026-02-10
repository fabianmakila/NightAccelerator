package fi.fabianadrian.speedsleep.speedup.task;

import fi.fabianadrian.speedsleep.SpeedSleep;
import fi.fabianadrian.speedsleep.config.section.CurveSection;
import org.bukkit.World;

public final class AdvanceNightTask implements Runnable {
	private final long ticksToAdd;
	private final World world;

	public AdvanceNightTask(SpeedSleep plugin, World world, int sleepingPlayerCount, int totalPlayerCount) {
		this.world = world;

		CurveSection config = plugin.config().curve();

		double sleepingPlayersRatio = (double) sleepingPlayerCount / totalPlayerCount;
		this.ticksToAdd = (long) interpolate(config.min(), config.max(), sleepingPlayersRatio, config.factor());
	}

	@Override
	public void run() {
		this.world.setTime(this.world.getTime() + ticksToAdd);
	}

	private double interpolate(double min, double max, double normalizedValue, double curveFactor) {
		if (curveFactor == 0) {
			return min + (max - min) * normalizedValue; // Linear interpolation
		}

		// Adjust the normalized value based on curve factor
		double adjustedValue;
		if (curveFactor > 0) {
			adjustedValue = Math.pow(normalizedValue, 1 + curveFactor);
		} else {
			adjustedValue = 1 - Math.pow(1 - normalizedValue, 1 - curveFactor);
		}

		return min + (max - min) * adjustedValue;
	}
}
