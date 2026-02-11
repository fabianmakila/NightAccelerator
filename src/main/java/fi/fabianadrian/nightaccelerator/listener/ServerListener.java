package fi.fabianadrian.nightaccelerator.listener;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public final class ServerListener implements Listener {
	private final NightAccelerator plugin;

	public ServerListener(NightAccelerator plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onStartup(ServerLoadEvent event) {
		this.plugin.load();
	}
}
