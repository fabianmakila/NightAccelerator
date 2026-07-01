package fi.fabianadrian.nightaccelerator;

import dev.faststats.ErrorTracker;
import dev.faststats.Metrics;
import dev.faststats.bukkit.BukkitContext;
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
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class NightAccelerator extends JavaPlugin {
	public static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware();
	public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
	private final ConfigManager configManager = new ConfigManager(this);
	private final PlaceholderManager placeholderManager = new PlaceholderManager(this);
	private final TagResolverFactory resolverFactory = new TagResolverFactory();
	private final WorldManager worldManager = new WorldManager(this);
	private final BukkitContext context = new BukkitContext.Factory(this, "41d5ebdb1c31671e9c9838eb77d68c4c")
			.errorTrackerService(ERROR_TRACKER)
			.metrics(Metrics.Factory::create)
			.create();


	public NightAccelerator() {
		new TranslationManager(getSLF4JLogger()).load();
	}

	@Override
	public void onEnable() {
		this.context.ready();

		registerListeners();

		getLifecycleManager().registerEventHandler(
				LifecycleEvents.COMMANDS,
				commands -> commands.registrar().register(
						NightAcceleratorCommandBrigadier.create(this)
				)
		);

		this.placeholderManager.register();
	}

	@Override
	public void onDisable() {
		this.context.shutdown();
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
