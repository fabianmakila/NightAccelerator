package fi.fabianadrian.nightaccelerator.placeholder;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.MiniPlaceholdersExpansion;
import org.bukkit.plugin.PluginManager;

public final class PlaceholderManager {
	private final NightAccelerator plugin;

	public PlaceholderManager(NightAccelerator plugin) {
		this.plugin = plugin;
	}

	public void register() {
		PluginManager manager = plugin.getServer().getPluginManager();
		if (manager.isPluginEnabled("MiniPlaceholders")) {
			new MiniPlaceholdersExpansion(this.plugin).register();
			this.plugin.resolverFactory().registerMiniPlaceholders();
		}
		if (manager.isPluginEnabled("PlaceholderAPI")) {
			//TODO PAPI Integration
		}
	}
}
