package fi.fabianadrian.nightaccelerator.world.acceleration;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.config.MainConfig;
import fi.fabianadrian.nightaccelerator.config.section.AccelerationSection;
import fi.fabianadrian.nightaccelerator.config.section.WeatherSection;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public final class AccelerationManager {
	private static final Random RANDOM = new Random();
	private final SleepWorld sleepWorld;
	private final BukkitScheduler scheduler = Bukkit.getScheduler();
	private final NightAccelerator plugin;
	private final AccelerateTask accelerateTask;
	private final MainConfig config;
	private BukkitTask bukkitTask;

	public AccelerationManager(NightAccelerator plugin, SleepWorld sleepWorld) {
		this.plugin = plugin;
		this.sleepWorld = sleepWorld;
		this.accelerateTask = new AccelerateTask(sleepWorld.world());
		this.config = plugin.config();
	}

	public void recalculate() {
		if (this.sleepWorld.sleeping().isEmpty()) {
			if (this.bukkitTask != null) {
				if (this.sleepWorld.isNightOver()) {
					onPostNight();
				}
				this.bukkitTask.cancel();
				this.bukkitTask = null;
			}
			return;
		}

		AccelerationSection config = this.plugin.config().acceleration();

		double sleepingPlayersRatio = (double) this.sleepWorld.sleeping().size() / this.sleepWorld.max();
		long ticksToAdd = (long) interpolate(config.min(), config.max(), sleepingPlayersRatio, config.factor());
		this.accelerateTask.ticksToAdd(ticksToAdd);

		if (this.bukkitTask == null) {
			this.bukkitTask = this.scheduler.runTaskTimer(this.plugin, this.accelerateTask, 0, config.updateRate());
		}
	}

	public void shutdown() {
		this.bukkitTask.cancel();
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

	private void onPostNight() {
		WeatherSection weatherConfig = this.config.weather();
		if (weatherConfig.clearEnabled() && !this.sleepWorld.world().isClearWeather()) {
			this.sleepWorld.world().setClearWeatherDuration(RANDOM.nextInt(weatherConfig.clearMax() - weatherConfig.clearMin() + 1) + weatherConfig.clearMin());
		}
	}
}
