package fi.fabianadrian.nightaccelerator.world.feature;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.config.section.AccelerationSection;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import org.bukkit.World;

public final class AccelerationFeature extends Feature {
	private final AccelerationSection config;
	private long ticksToAdvance = 0;

	public AccelerationFeature(NightAccelerator plugin, SleepWorld sleepWorld) {
		super(plugin, sleepWorld);
		this.config = plugin.config().acceleration();
	}

	@Override
	protected void update() {
		World world = super.world.world();
		switch (this.config.accelerateWeather()) {
			case RAIN -> {
				if (world.hasStorm()) {
					world.setWeatherDuration((int) (world.getWeatherDuration() - this.ticksToAdvance));
				}
				if (world.isThundering()) {
					world.setThunderDuration((int) (world.getThunderDuration() - this.ticksToAdvance));
				}
			}
			case ALWAYS -> {
				world.setWeatherDuration((int) (world.getWeatherDuration() - this.ticksToAdvance));
				world.setThunderDuration((int) (world.getThunderDuration() - this.ticksToAdvance));
			}
		}
		world.setTime(world.getTime() + this.ticksToAdvance);

		if (super.world.sleepProgress() >= 1) {
			super.world.onPostNight();
		}
	}

	@Override
	protected int updateRate() {
		return this.config.updateRate();
	}

	@Override
	public void recalculate() {
		AccelerationSection config = this.plugin.config().acceleration();

		double sleepingPlayersRatio = (double) super.world.sleeping().size() / super.world.max();
		this.ticksToAdvance = (long) interpolate(config.min(), config.max(), sleepingPlayersRatio, config.factor());
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
