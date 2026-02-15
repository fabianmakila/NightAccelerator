package fi.fabianadrian.nightaccelerator.tagresolver;

import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.Locale;

public final class TagResolverFactory {
	private final TagResolver pointeredResolver = new PointeredResolver();
	private boolean miniPlaceholders = false;
	private Locale defaultLocale = Locale.ENGLISH;

	public TagResolver resolver(SleepWorld world) {
		TagResolver.Builder builder = builder(world);

		if (this.miniPlaceholders) {
			builder = builder.resolver(MiniPlaceholders.audienceGlobalPlaceholders());
		}

		return builder.build();
	}

	public void registerMiniPlaceholders() {
		this.miniPlaceholders = true;
	}

	public void defaultLocale(Locale locale) {
		this.defaultLocale = locale;
	}

	private TagResolver.Builder builder(SleepWorld world) {
		return TagResolver.builder().resolvers(
				Placeholder.unparsed("sleeping", String.valueOf(world.sleeping().size())),
				Placeholder.unparsed("max", String.valueOf(world.max())),
				new TimeTagResolver(world, this.defaultLocale),
				this.pointeredResolver
		);
	}
}
