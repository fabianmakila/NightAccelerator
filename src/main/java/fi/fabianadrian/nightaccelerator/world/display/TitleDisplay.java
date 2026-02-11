package fi.fabianadrian.nightaccelerator.world.display;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.config.section.TitleSection;
import fi.fabianadrian.nightaccelerator.placeholder.TagResolverFactory;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
		this.config = plugin.config().title();
		this.resolverFactory = plugin.resolverFactory();
	}

	@Override
	public void update() {
		sendSleepingTitle();
	}

	private void sendSleepingTitle() {
		this.world.sleeping().forEach(player -> {
			TagResolver tagResolver = this.resolverFactory.resolver(this.world, player);

			Component title = MiniMessage.miniMessage().deserialize(this.config.title(), tagResolver);
			Component subtitle = MiniMessage.miniMessage().deserialize(this.config.subtitle(), tagResolver);
			player.sendTitlePart(TitlePart.TIMES, TIMES);
			player.sendTitlePart(TitlePart.TITLE, title);
			player.sendTitlePart(TitlePart.SUBTITLE, subtitle);
		});
	}
}
