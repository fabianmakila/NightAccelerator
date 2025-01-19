package fi.fabianadrian.speedsleep.listener;

import fi.fabianadrian.speedsleep.SpeedSleep;
import fi.fabianadrian.speedsleep.speedup.WorldManager;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerListener implements Listener {
	private final WorldManager speedupManager;

	public PlayerListener(SpeedSleep plugin) {
		this.speedupManager = plugin.speedupManager();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		this.speedupManager.recalculate(event.getPlayer().getWorld().getUID());
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		this.speedupManager.recalculate(event.getPlayer().getWorld().getUID());
	}

	@EventHandler
	public void onGamemodeChange(PlayerGameModeChangeEvent event) {
		this.speedupManager.recalculate(event.getPlayer().getWorld().getUID());
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		this.speedupManager.recalculate(event.getFrom().getUID());
		this.speedupManager.recalculate(event.getPlayer().getWorld().getUID());
	}

	@EventHandler
	public void onDeepSleep(PlayerDeepSleepEvent event) {
		event.setCancelled(true);
	}
}
