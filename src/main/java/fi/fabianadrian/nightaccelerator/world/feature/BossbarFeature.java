package fi.fabianadrian.nightaccelerator.world.feature;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.config.section.BossbarSection;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public final class BossbarFeature extends Feature {
	private final BossbarSection config;
	private final BossBar bossBar;
	private final Server server;

	public BossbarFeature(NightAccelerator plugin, SleepWorld world) {
		super(plugin, world);
		this.config = plugin.config().bossbar();
		this.server = plugin.getServer();

		this.bossBar = BossBar.bossBar(name(), 0, this.config.color(), this.config.overlay());
	}

	@Override
	public void start() {
		this.bossBar.addViewer(super.world.world());
		super.start();
	}

	@Override
	public void stop() {
		this.bossBar.removeViewer(this.server);
		super.stop();
	}

	@Override
	protected int updateRate() {
		return this.config.updateRate();
	}

	public void add(Player player) {
		this.bossBar.addViewer(player);
	}

	public void remove(Player player) {
		this.bossBar.removeViewer(player);
	}

	protected void update() {
		this.bossBar.name(name());
		this.bossBar.progress(super.world.sleepProgress());
	}

	private Component name() {
		return NightAccelerator.MINI_MESSAGE.deserialize(this.config.title(), super.resolverFactory.resolver(super.world));
	}
}
