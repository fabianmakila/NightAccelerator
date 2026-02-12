package fi.fabianadrian.nightaccelerator.world.display;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.config.section.DisplaySection;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class DisplayManager {
	private final ScheduledFuture<?> scheduledFuture;
	private final List<Display> displays = new ArrayList<>();
	private final NightAccelerator plugin;

	public DisplayManager(NightAccelerator plugin, SleepWorld world) {
		this.plugin = plugin;

		DisplaySection config = plugin.config().display();
		this.scheduledFuture = plugin.executorService().scheduleAtFixedRate(this::update, 0, config.updateRate(), TimeUnit.MILLISECONDS);

		if (config.bossbar().enabled()) {
			this.displays.add(new BossbarDisplay(plugin, world));
		}
		if (config.title().enabled()) {
			this.displays.add(new TitleDisplay(plugin, world));
		}
	}

	public void shutdown() {
		this.scheduledFuture.cancel(true);
		this.displays.forEach(Display::shutdown);
	}

	private void update() {
		Bukkit.getScheduler().runTask(this.plugin, () -> this.displays.forEach(Display::update));
	}
}
