package fi.fabianadrian.speedsleep.resolver;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.World;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public final class SleepTagResolver {
	public static TagResolver worldTime(World world, Audience audience) {
		int worldTime = (int) world.getTime() + 6000;
		int hours = (worldTime / 1000) % 24; // Each 1000 ticks = 1 hour
		int minutes = (worldTime % 1000) * 60 / 1000; // Convert remaining ticks to minutes
		LocalTime time = LocalTime.of(hours, minutes);
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(audience.getOrDefault(Identity.LOCALE, Locale.getDefault()));
		return Placeholder.unparsed("world_time", formatter.format(time));
	}
}
