package fi.fabianadrian.speedsleep;

import fi.fabianadrian.speedsleep.config.ConfigManager;
import fi.fabianadrian.speedsleep.listener.BedListener;
import fi.fabianadrian.speedsleep.listener.PlayerListener;
import fi.fabianadrian.speedsleep.speedup.WorldManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.ConfigurateException;

import java.util.List;

public final class SpeedSleep extends JavaPlugin {
	private WorldManager speedupManager;
	private ConfigManager configManager;

	@Override
	public void onEnable() {
		this.configManager = new ConfigManager(getDataPath());
		try {
			this.configManager.reload();
		} catch (ConfigurateException e) {
			getSLF4JLogger().error("Could not load configuration", e);
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		this.speedupManager = new WorldManager(this);
		registerListeners();
	}

	public ConfigManager configManager() {
		return this.configManager;
	}

	public WorldManager speedupManager() {
		return this.speedupManager;
	}

	private void registerListeners() {
		PluginManager manager = getServer().getPluginManager();
		List.of(
				new BedListener(this),
				new PlayerListener(this)
		).forEach(listener -> manager.registerEvents(listener, this));
	}
}
