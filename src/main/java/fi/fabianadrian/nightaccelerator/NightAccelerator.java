package fi.fabianadrian.nightaccelerator;

import fi.fabianadrian.nightaccelerator.command.NightAcceleratorCommand;
import fi.fabianadrian.nightaccelerator.config.ConfigManager;
import fi.fabianadrian.nightaccelerator.config.MainConfig;
import fi.fabianadrian.nightaccelerator.listener.BedListener;
import fi.fabianadrian.nightaccelerator.listener.PlayerListener;
import fi.fabianadrian.nightaccelerator.listener.ServerListener;
import fi.fabianadrian.nightaccelerator.listener.ThunderListener;
import fi.fabianadrian.nightaccelerator.locale.TranslationManager;
import fi.fabianadrian.nightaccelerator.placeholder.PlaceholderManager;
import fi.fabianadrian.nightaccelerator.tagresolver.TagResolverFactory;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class NightAccelerator extends JavaPlugin {
	public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
	private final ConfigManager configManager = new ConfigManager(this);
	private final PlaceholderManager placeholderManager = new PlaceholderManager(this);
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	private final TagResolverFactory resolverFactory = new TagResolverFactory();
	private final WorldManager worldManager = new WorldManager(this);

	public NightAccelerator() {
		new TranslationManager(getSLF4JLogger()).load();
	}

	@Override
	public void onEnable() {
		registerListeners();
		new NightAcceleratorCommand(this).register();
		this.placeholderManager.register();
		new Metrics(this, 29528);
	}

	public void load() {
		this.configManager.load();
		this.resolverFactory.defaultLocale(this.config().defaultLocale());
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
				new ServerListener(this),
				new ThunderListener(this)
		).forEach(listener -> manager.registerEvents(listener, this));
	}
}
