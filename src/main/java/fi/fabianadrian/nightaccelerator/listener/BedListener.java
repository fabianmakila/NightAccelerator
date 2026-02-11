package fi.fabianadrian.nightaccelerator.listener;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public final class BedListener implements Listener {
	private final WorldManager speedupManager;

	public BedListener(NightAccelerator plugin) {
		this.speedupManager = plugin.worldManager();
	}

	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent event) {
		if (event.isCancelled()) {
			return;
		}
		this.speedupManager.recalculate(event.getPlayer().getWorld());
	}

	@EventHandler
	public void onBedLeave(PlayerBedLeaveEvent event) {
		if (event.isCancelled()) {
			return;
		}
		this.speedupManager.recalculate(event.getPlayer().getWorld());
	}
}
