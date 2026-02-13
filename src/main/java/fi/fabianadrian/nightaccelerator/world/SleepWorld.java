package fi.fabianadrian.nightaccelerator.world;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.night.NightRange;
import fi.fabianadrian.nightaccelerator.world.acceleration.AccelerationManager;
import fi.fabianadrian.nightaccelerator.world.display.DisplayManager;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class SleepWorld {
	private final List<Player> sleeping = new ArrayList<>();
	private final World world;
	private final AccelerationManager accelerationManager;
	private final DisplayManager displayManager;
	private int max = 0;

	public SleepWorld(NightAccelerator plugin, World world) {
		this.world = world;
		this.accelerationManager = new AccelerationManager(plugin, this);
		this.displayManager = new DisplayManager(plugin, this);
	}

	public void shutdown() {
		this.displayManager.shutdown();
		this.accelerationManager.shutdown();
	}

	public void recalculate() {
		this.max = 0;
		this.sleeping.clear();

		for (Player player : this.world.getPlayers()) {
			if (player.getGameMode() == GameMode.SPECTATOR) {
				continue;
			}

			this.max++;

			if (player.isSleeping()) {
				this.sleeping.add(player);
			}
		}

		this.accelerationManager.recalculate();
	}

	public List<Player> sleeping() {
		return List.copyOf(this.sleeping);
	}

	public int max() {
		return this.max;
	}

	public World world() {
		return this.world;
	}

	public NightRange nightRange() {
		return this.world.isClearWeather() ? NightRange.CLEAR : NightRange.RAIN;
	}

	public float nightProgress() {
		return nightRange().progress(this.world.getTime());
	}

	public boolean isNightOver() {
		return !nightRange().isInRange(this.world.getTime());
	}

	public String time(Locale locale) {
		int worldTime = (int) world.getTime() + 6000;
		int hours = (worldTime / 1000) % 24; // Each 1000 ticks = 1 hour
		int minutes = (worldTime % 1000) * 60 / 1000; // Convert remaining ticks to minutes
		LocalTime time = LocalTime.of(hours, minutes);
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale);
		return formatter.format(time);
	}
}
