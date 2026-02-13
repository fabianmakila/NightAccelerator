package fi.fabianadrian.nightaccelerator.tagresolver;

import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.pointer.Pointered;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.Locale;

public final class TagResolverFactory {
	private boolean miniPlaceholders = false;

	public TagResolver resolver(SleepWorld world) {
		var builder = TagResolver.builder().resolvers(
				Placeholder.unparsed("sleeping", String.valueOf(world.sleeping())),
				Placeholder.unparsed("max", String.valueOf(world.max()))
		).tag("time", ((queue, context) -> {
			Locale locale;
			Pointered pointered = context.target();
			if (pointered == null) {
				locale = Locale.getDefault();
			} else {
				locale = pointered.getOrDefault(Identity.LOCALE, Locale.getDefault());
			}
			return Tag.preProcessParsed(world.time(locale));
		}));

		if (this.miniPlaceholders) {
			builder = builder.resolver(MiniPlaceholders.audienceGlobalPlaceholders());
		}

		return builder.build();
	}

	public void registerMiniPlaceholders() {
		this.miniPlaceholders = true;
	}
}
