package fi.fabianadrian.nightaccelerator.world;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.config.MainConfig;
import fi.fabianadrian.nightaccelerator.config.section.MorningSection;
import fi.fabianadrian.nightaccelerator.world.feature.FeatureManager;
import org.bukkit.Bukkit;
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
	private final MainConfig config;
	private final WeatherManager weatherManager;
	private final NightAccelerator plugin;
	private final FeatureManager featureManager;
	private int max = 0;
	private long thunderStartedAt = 0;
	private boolean recalculationQueued = false;

	public SleepWorld(NightAccelerator plugin, World world) {
		this.plugin = plugin;
		this.world = world;
		this.config = plugin.config();
		this.weatherManager = new WeatherManager(world);
		this.featureManager = new FeatureManager(plugin, this);

		recalculate();
	}

	public void shutdown() {
		this.featureManager.stop();
	}

	// Player is joining the world from some other world
	public void onJoin(Player player) {
		this.featureManager.track(player);
		queueRecalculation();
	}

	// Player is either leaving the server or moving to another world
	public void onLeave(Player player) {
		this.featureManager.untrack(player);
		queueRecalculation();
	}

	// Ensures that only one recalculation will happen per tick
	// Also delays the recalculation by 1 tick which means that canceled events get processed correctly
	public void queueRecalculation() {
		if (this.recalculationQueued) {
			return;
		}

		this.recalculationQueued = true;

		Bukkit.getScheduler().runTask(this.plugin, () -> {
			this.recalculationQueued = false;
			recalculate();
		});
	}

	private void recalculate() {
		this.sleeping.clear();
		this.world.getPlayers().forEach(player -> {
			if (player.isSleeping()) {
				this.sleeping.add(player);
			}
		});

		if (this.sleeping.isEmpty()) {
			this.featureManager.stop();
			return;
		}

		this.max = 0;
		for (Player player : this.world.getPlayers()) {
			// Always count player if sleeping
			if (!player.isSleeping() && (player.getGameMode() == GameMode.SPECTATOR || player.isSleepingIgnored() || player.hasPermission("nightaccelerator.exclude"))) {
				continue;
			}
			this.max++;
		}
		this.featureManager.recalculate();
		this.featureManager.start();
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
		if (this.world.isThundering()) {
			this.sleeping.forEach(player -> {
				if (player.isSleeping()) {
					player.wakeup(false);
				}
			});
		}

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
