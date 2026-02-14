package fi.fabianadrian.nightaccelerator.tagresolver;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.pointer.Pointered;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public final class PointeredResolver implements TagResolver {
	private static final List<String> TAGS = List.of("name", "display_name", "uuid");

	@Override
	public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue arguments, @NotNull Context ctx) throws ParsingException {
		Pointered pointered = ctx.target();
		if (pointered == null) {
			return null;
		}
		switch (name.toLowerCase(Locale.ROOT)) {
			case "name" -> {
				return Tag.preProcessParsed(pointered.getOrDefault(Identity.NAME, "unknown"));
			}
			case "display_name" -> {
				return Tag.selfClosingInserting(pointered.getOrDefault(Identity.DISPLAY_NAME, Component.text("unknown")));
			}
			case "uuid" -> {
				UUID uuid = pointered.getOrDefault(Identity.UUID, null);
				String uuidAsString = uuid == null ? "unknown" : uuid.toString();
				return Tag.preProcessParsed(uuidAsString);
			}
		}
		return null;
	}

	@Override
	public boolean has(@NotNull String name) {
		return TAGS.contains(name.toLowerCase(Locale.ROOT));
	}
}
