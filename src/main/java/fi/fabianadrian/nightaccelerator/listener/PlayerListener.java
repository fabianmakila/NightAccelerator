package fi.fabianadrian.nightaccelerator.listener;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.event.EventHandler;
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
		this.worldManager.recalculate(event.getPlayer().getWorld());
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		this.worldManager.recalculate(event.getPlayer().getWorld());
	}

	@EventHandler
	public void onGamemodeChange(PlayerGameModeChangeEvent event) {
		this.worldManager.recalculate(event.getPlayer().getWorld());
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		this.worldManager.recalculate(event.getFrom());
		this.worldManager.recalculate(event.getPlayer().getWorld());
	}

	@EventHandler
	public void onDeepSleep(PlayerDeepSleepEvent event) {
		event.setCancelled(true);
	}
}
