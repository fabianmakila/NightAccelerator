package fi.fabianadrian.nightaccelerator;

import fi.fabianadrian.nightaccelerator.config.ConfigManager;
import fi.fabianadrian.nightaccelerator.config.MainConfig;
import fi.fabianadrian.nightaccelerator.listener.BedListener;
import fi.fabianadrian.nightaccelerator.listener.PlayerListener;
import fi.fabianadrian.nightaccelerator.listener.ServerListener;
import fi.fabianadrian.nightaccelerator.placeholder.TagResolverFactory;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class NightAccelerator extends JavaPlugin {
	private ConfigManager configManager;
	private WorldManager worldManager;
	private TagResolverFactory resolverFactory;
	private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	@Override
	public void onEnable() {
		this.configManager = new ConfigManager(this);
		this.worldManager = new WorldManager(this);
		registerListeners();

		if (getServer().getPluginManager().isPluginEnabled("MiniPlaceholders")) {

		} else {
			this.resolverFactory = new TagResolverFactory();
		}
	}

	public void load() {
		this.configManager.load();
		this.worldManager.load();
	}

	public MainConfig config() {
		return this.configManager.config();
	}

	public WorldManager worldManager() {
		return this.worldManager;
	}

	public TagResolverFactory resolverFactory() {
		return this.resolverFactory;
	}

	public ScheduledExecutorService executorService() {
		return this.executorService;
	}

	private void registerListeners() {
		PluginManager manager = getServer().getPluginManager();
		List.of(
				new BedListener(this),
				new PlayerListener(this),
				new ServerListener(this)
		).forEach(listener -> manager.registerEvents(listener, this));
	}
}
