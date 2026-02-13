package fi.fabianadrian.nightaccelerator.world.display;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.config.section.BossbarSection;
import fi.fabianadrian.nightaccelerator.tagresolver.TagResolverFactory;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;

public final class BossbarDisplay implements Display {
	private final TagResolverFactory resolverFactory;
	private final BossbarSection config;
	private final SleepWorld world;
	private final BossBar bossBar;
	private final Server server;

	public BossbarDisplay(NightAccelerator plugin, SleepWorld world) {
		this.resolverFactory = plugin.resolverFactory();
		this.config = plugin.config().display().bossbar();
		this.world = world;
		this.server = plugin.getServer();

		this.bossBar = BossBar.bossBar(name(), 0, this.config.color(), this.config.overlay());
	}

	@Override
	public void update() {
		if (this.world.sleeping().isEmpty()) {
			this.bossBar.removeViewer(this.server);
			return;
		}

		this.bossBar.addViewer(this.server);
		this.bossBar.name(name());
		this.bossBar.progress(this.world.nightProgress());
	}

	@Override
	public void shutdown() {
		this.bossBar.removeViewer(this.server);
	}

	private Component name() {
		return NightAccelerator.MINI_MESSAGE.deserialize(this.config.title(), this.resolverFactory.resolver(this.world));
	}
}
