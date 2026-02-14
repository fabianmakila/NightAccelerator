package fi.fabianadrian.nightaccelerator.world.display;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.config.section.TitleSection;
import fi.fabianadrian.nightaccelerator.tagresolver.TagResolverFactory;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;

import java.time.Duration;

public final class TitleDisplay implements Display {
	private static final Title.Times TIMES = Title.Times.times(Duration.ZERO, Duration.ofSeconds(1), Duration.ofSeconds(1));
	private final SleepWorld world;
	private final TitleSection config;
	private final TagResolverFactory resolverFactory;

	public TitleDisplay(NightAccelerator plugin, SleepWorld world) {
		this.world = world;
		this.config = plugin.config().display().title();
		this.resolverFactory = plugin.resolverFactory();
	}

	@Override
	public void update() {
		sendTitle(this.config.sleepingTitle(), this.config.sleepingSubtitle());
	}

	@Override
	public void shutdown() {

	}

	@Override
	public void morning() {
		sendTitle(this.config.morningTitle(), this.config.morningSubtitle());
	}

	private void sendTitle(String titleString, String subtitleString) {
		this.world.sleeping().forEach(player -> {
			TagResolver tagResolver = this.resolverFactory.resolver(this.world);

			Component title = NightAccelerator.MINI_MESSAGE.deserialize(titleString, player, tagResolver);
			Component subtitle = NightAccelerator.MINI_MESSAGE.deserialize(subtitleString, player, tagResolver);
			player.sendTitlePart(TitlePart.TIMES, TIMES);
			player.sendTitlePart(TitlePart.TITLE, title);
			player.sendTitlePart(TitlePart.SUBTITLE, subtitle);
		});
	}
}
