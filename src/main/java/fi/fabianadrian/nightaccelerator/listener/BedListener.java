package fi.fabianadrian.nightaccelerator.listener;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public final class BedListener implements Listener {
	private final WorldManager worldManager;

	public BedListener(NightAccelerator plugin) {
		this.worldManager = plugin.worldManager();
	}

	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent event) {
		SleepWorld world = this.worldManager.world(event.getPlayer().getWorld());
		if (world != null) {
			world.addSleeper(event.getPlayer());
		}
	}

	@EventHandler
	public void onBedLeave(PlayerBedLeaveEvent event) {
		SleepWorld world = this.worldManager.world(event.getPlayer().getWorld());
		if (world != null) {
			world.removeSleeper(event.getPlayer());
		}
	}
}
