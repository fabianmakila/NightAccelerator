package fi.fabianadrian.nightaccelerator.listener;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;

public final class ThunderListener implements Listener {
	private final WorldManager worldManager;

	public ThunderListener(NightAccelerator plugin) {
		this.worldManager = plugin.worldManager();
	}

	@EventHandler
	public void onThunderChange(ThunderChangeEvent event) {
		this.worldManager.world(event.getWorld()).thunderStarted();
	}
}
