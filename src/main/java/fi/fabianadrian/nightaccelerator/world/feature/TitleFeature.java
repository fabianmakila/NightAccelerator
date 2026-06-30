package fi.fabianadrian.nightaccelerator.world.feature;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.config.section.TitleSection;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;

import java.time.Duration;

public final class TitleFeature extends Feature {
	private static final Title.Times TIMES = Title.Times.times(Duration.ZERO, Duration.ofSeconds(1), Duration.ofSeconds(1));
	private final TitleSection config;

	public TitleFeature(NightAccelerator plugin, SleepWorld world) {
		super(plugin, world);
		this.config = plugin.config().title();
	}

	@Override
	public void stop() {
		super.stop();
		if (super.world.sleepProgress() >= 1) {
			sendTitle(this.config.morningTitle(), this.config.morningSubtitle());
		}
	}

	@Override
	protected int updateRate() {
		return this.config.updateRate();
	}

	protected void update() {
		sendTitle(this.config.sleepingTitle(), this.config.sleepingSubtitle());
	}

	private void sendTitle(String titleString, String subtitleString) {
		this.world.sleeping().forEach(player -> {
			TagResolver tagResolver = super.resolverFactory.resolver(super.world);

			Component title = NightAccelerator.MINI_MESSAGE.deserialize(titleString, player, tagResolver);
			Component subtitle = NightAccelerator.MINI_MESSAGE.deserialize(subtitleString, player, tagResolver);
			player.sendTitlePart(TitlePart.TIMES, TIMES);
			player.sendTitlePart(TitlePart.TITLE, title);
			player.sendTitlePart(TitlePart.SUBTITLE, subtitle);
		});
	}
}
