package fi.fabianadrian.speedsleep.listener;

import fi.fabianadrian.speedsleep.SpeedSleep;
import fi.fabianadrian.speedsleep.speedup.WorldManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public final class BedListener implements Listener {
	private final WorldManager speedupManager;

	public BedListener(SpeedSleep plugin) {
		this.speedupManager = plugin.speedupManager();
	}

	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent event) {
		if (event.isCancelled()) {
			return;
		}
		this.speedupManager.recalculate(event.getPlayer().getWorld().getUID());
	}

	@EventHandler
	public void onBedLeave(PlayerBedLeaveEvent event) {
		if (event.isCancelled()) {
			return;
		}
		this.speedupManager.recalculate(event.getPlayer().getWorld().getUID());
	}
}
