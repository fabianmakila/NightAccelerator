package fi.fabianadrian.nightaccelerator.world;

import org.bukkit.World;

public final class WeatherManager {
	private final World world;

	public WeatherManager(World world) {
		this.world = world;
	}

	public void clear() {
		if (this.world.hasStorm()) {
			this.world.setStorm(false);
		}
		if (this.world.isThundering()) {
			this.world.setThundering(false);
		}
	}
}
