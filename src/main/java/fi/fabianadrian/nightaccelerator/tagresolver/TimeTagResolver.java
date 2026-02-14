package fi.fabianadrian.nightaccelerator.tagresolver;

import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.pointer.Pointered;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public final class TimeTagResolver implements TagResolver {
	private final SleepWorld world;

	public TimeTagResolver(SleepWorld world) {
		this.world = world;
	}

	@Override
	public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue arguments, @NotNull Context context) throws ParsingException {
		Locale locale;
		Pointered pointered = context.target();
		if (pointered == null) {
			locale = Locale.getDefault();
		} else {
			locale = pointered.getOrDefault(Identity.LOCALE, Locale.getDefault());
		}
		return Tag.preProcessParsed(this.world.time(locale));
	}

	@Override
	public boolean has(@NotNull String name) {
		return name.equalsIgnoreCase("time");
	}
}
