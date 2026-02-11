package fi.fabianadrian.nightaccelerator.world.acceleration;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.config.section.CurveSection;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public final class AccelerationManager {
	private final SleepWorld sleepWorld;
	private final BukkitScheduler scheduler = Bukkit.getScheduler();
	private final NightAccelerator plugin;
	private final AccelerateTask accelerateTask;
	private BukkitTask bukkitTask;

	public AccelerationManager(NightAccelerator plugin, SleepWorld sleepWorld) {
		this.plugin = plugin;
		this.sleepWorld = sleepWorld;
		this.accelerateTask = new AccelerateTask(sleepWorld.world());
	}

	public void recalculate() {
		if (this.sleepWorld.sleeping().isEmpty()) {
			this.bukkitTask.cancel();
			this.bukkitTask = null;
			return;
		}

		CurveSection config = this.plugin.config().curve();

		double sleepingPlayersRatio = (double) this.sleepWorld.sleeping().size() / this.sleepWorld.max();
		long ticksToAdd = (long) interpolate(config.min(), config.max(), sleepingPlayersRatio, config.factor());
		this.accelerateTask.ticksToAdd(ticksToAdd);

		if (this.bukkitTask == null) {
			this.bukkitTask = this.scheduler.runTaskTimer(plugin, this.accelerateTask, 0, 1);
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
}
