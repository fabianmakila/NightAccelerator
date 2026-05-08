package fi.fabianadrian.nightaccelerator.world.acceleration;

import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import org.bukkit.World;

public final class AccelerateTask implements Runnable {
	private final World world;
	private final AccelerateWeather accelerateWeather;
	private final SleepWorld sleepWorld;
	private long ticksToAdvance = 0;

	public AccelerateTask(SleepWorld sleepWorld, AccelerateWeather accelerateWeather) {
		this.sleepWorld = sleepWorld;
		this.world = sleepWorld.world();
		this.accelerateWeather = accelerateWeather;
	}

	public void ticksToAdvance(long ticksToAdvance) {
		this.ticksToAdvance = ticksToAdvance;
	}

	@Override
	public void run() {
		switch (this.accelerateWeather) {
			case RAIN -> {
				if (this.world.hasStorm()) {
					this.world.setWeatherDuration((int) (this.world.getWeatherDuration() - this.ticksToAdvance));
				}
				if (this.world.isThundering()) {
					this.world.setThunderDuration((int) (this.world.getThunderDuration() - this.ticksToAdvance));
				}
			}
			case ALWAYS -> {
				this.world.setWeatherDuration((int) (this.world.getWeatherDuration() - this.ticksToAdvance));
				this.world.setThunderDuration((int) (this.world.getThunderDuration() - this.ticksToAdvance));
			}
		}
		this.world.setTime(this.world.getTime() + this.ticksToAdvance);

		if (this.sleepWorld.sleepProgress() >= 1) {
			this.sleepWorld.onPostNight();
		}
	}
}
