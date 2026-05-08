package fi.fabianadrian.nightaccelerator.world;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.config.MainConfig;
import fi.fabianadrian.nightaccelerator.config.section.MorningSection;
import fi.fabianadrian.nightaccelerator.night.SleepWindow;
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
	private final MainConfig config;
	private final WeatherManager weatherManager;
	private int max = 0;
	private long thunderStartedAt = 0;

	public SleepWorld(NightAccelerator plugin, World world) {
		this.world = world;
		this.accelerationManager = new AccelerationManager(plugin, this);
		this.displayManager = new DisplayManager(plugin, this);
		this.config = plugin.config();
		this.weatherManager = new WeatherManager(world);
	}

	public void shutdown() {
		this.displayManager.shutdown();
		this.accelerationManager.shutdown();
	}

	public void recalculate() {
		this.max = 0;
		this.sleeping.clear();

		for (Player player : this.world.getPlayers()) {
			if (player.getGameMode() == GameMode.SPECTATOR || player.isSleepingIgnored()) {
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

	/**
	 * @return A float between 0 and 1 indicating the sleep progress
	 */
	public float sleepProgress() {
		SleepWindow window = this.world.isClearWeather() ? SleepWindow.CLEAR : SleepWindow.RAIN;

		long start = this.world.isThundering() ? this.thunderStartedAt : window.start;
		long end = window.end;
		if (this.world.isThundering() && !this.config.morning().clearWeather()) {
			end = (this.world.getTime() + this.world.getThunderDuration()) % 24000;
		}

		long time = this.world.getTime();

		long relativeTime = wrapAroundDifference(start, time);
		long duration = wrapAroundDifference(start, end);

		if (duration == 0) {
			return 1;
		}

		return Math.min(1f, (float) relativeTime / duration);
	}

	/**
	 * @return The positive difference from start to now, wrapping over 24000 ticks.
	 */
	private long wrapAroundDifference(long start, long now) {
		return (now - start + 24000) % 24000;
	}

	public String formattedTime(Locale locale) {
		int worldTime = (int) world.getTime() + 6000;
		int hours = (worldTime / 1000) % 24; // Each 1000 ticks = 1 hour
		int minutes = (worldTime % 1000) * 60 / 1000; // Convert remaining ticks to minutes
		LocalTime time = LocalTime.of(hours, minutes);
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale);
		return formatter.format(time);
	}

	public void thunderStarted() {
		this.thunderStartedAt = this.world.getTime();
	}

	public void onPostNight() {
		this.displayManager.morning();

		MorningSection morningConfig = this.config.morning();
		if (morningConfig.clearWeather()) {
			this.weatherManager.clear();
		}
		String sound = morningConfig.sound();
		if (!sound.isEmpty()) {
			this.sleeping.forEach(player -> player.playSound(player, sound, morningConfig.soundVolume(), morningConfig.soundPitch()));
		}
	}
}
