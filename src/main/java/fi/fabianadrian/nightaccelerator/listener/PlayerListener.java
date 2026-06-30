package fi.fabianadrian.nightaccelerator.listener;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerListener implements Listener {
	private final WorldManager worldManager;

	public PlayerListener(NightAccelerator plugin) {
		this.worldManager = plugin.worldManager();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		SleepWorld world = this.worldManager.world(event.getPlayer().getWorld());
		if (world != null) {
			world.onJoin(event.getPlayer());
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		SleepWorld world = this.worldManager.world(event.getPlayer().getWorld());
		if (world != null) {
			world.onLeave(event.getPlayer());
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onGamemodeChange(PlayerGameModeChangeEvent event) {
		// Only need to do a recalculation when new game mode is spectator since it's the only game mode that can't sleep
		if (event.getNewGameMode() != GameMode.SPECTATOR) {
			return;
		}
		SleepWorld world = this.worldManager.world(event.getPlayer().getWorld());
		if (world != null) {
			world.queueRecalculation();
		}
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		SleepWorld from = this.worldManager.world(event.getPlayer().getWorld());
		if (from != null) {
			from.onLeave(event.getPlayer());
		}
		SleepWorld to = this.worldManager.world(event.getPlayer().getWorld());
		if (to != null) {
			to.onJoin(event.getPlayer());
		}
	}

	@EventHandler
	public void onDeepSleep(PlayerDeepSleepEvent event) {
		event.setCancelled(true);
	}
}
