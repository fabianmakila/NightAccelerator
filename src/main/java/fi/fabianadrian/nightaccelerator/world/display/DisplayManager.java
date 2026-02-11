package fi.fabianadrian.nightaccelerator.world.display;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class DisplayManager {
	private final ScheduledFuture<?> scheduledFuture;
	private TitleDisplay titleDisplay;

	public DisplayManager(NightAccelerator plugin, SleepWorld world) {
		this.scheduledFuture = plugin.executorService().scheduleAtFixedRate(this::update, 0, 1, TimeUnit.SECONDS);
		this.titleDisplay = new TitleDisplay(plugin, world);
	}

	public void shutdown() {
		this.scheduledFuture.cancel(true);
	}

	private void update() {
		this.titleDisplay.update();
	}
}
