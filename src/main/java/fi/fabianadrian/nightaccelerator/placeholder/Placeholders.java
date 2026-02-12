package fi.fabianadrian.nightaccelerator.placeholder;

import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public abstract class Placeholders {
	protected String time(World world) {
		return time(world, Locale.getDefault());
	}

	protected String time(Player player) {
		return time(player.getWorld(), player.locale());
	}

	private String time(World world, Locale locale) {
		int worldTime = (int) world.getTime() + 6000;
		int hours = (worldTime / 1000) % 24; // Each 1000 ticks = 1 hour
		int minutes = (worldTime % 1000) * 60 / 1000; // Convert remaining ticks to minutes
		LocalTime time = LocalTime.of(hours, minutes);
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale);
		return formatter.format(time);
	}

	protected int sleeping(SleepWorld world) {
		return world.sleeping().size();
	}

	protected int max(SleepWorld world) {
		return world.max();
	}
}
