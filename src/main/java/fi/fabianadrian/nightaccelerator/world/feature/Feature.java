package fi.fabianadrian.nightaccelerator.world.feature;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.tagresolver.TagResolverFactory;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public abstract class Feature {
	protected final SleepWorld world;
	protected final NightAccelerator plugin;
	final TagResolverFactory resolverFactory;
	private BukkitTask updateTask;

	public Feature(NightAccelerator plugin, SleepWorld world) {
		this.plugin = plugin;
		this.world = world;
		this.resolverFactory = plugin.resolverFactory();
	}

	public void start() {
		this.updateTask = Bukkit.getScheduler().runTaskTimer(this.plugin, this::update, 0, updateRate());
	}

	public void stop() {
		this.updateTask.cancel();
		this.updateTask = null;
	}

	public void recalculate() {

	}

	public boolean running() {
		return this.updateTask != null;
	}

	protected abstract void update();

	protected abstract int updateRate();
}
