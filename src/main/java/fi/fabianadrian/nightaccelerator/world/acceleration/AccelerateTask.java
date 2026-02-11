package fi.fabianadrian.nightaccelerator.world.acceleration;

import org.bukkit.World;

public final class AccelerateTask implements Runnable {
	private final World world;
	private long ticksToAdd = 0;

	public AccelerateTask(World world) {
		this.world = world;
	}

	public void ticksToAdd(long ticksToAdd) {
		this.ticksToAdd = ticksToAdd;
	}

	@Override
	public void run() {
		this.world.setTime(this.world.getTime() + this.ticksToAdd);
	}
}
