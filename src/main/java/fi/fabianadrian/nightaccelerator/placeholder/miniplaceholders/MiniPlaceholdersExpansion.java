package fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.placeholder.Placeholders;
import fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.audience.AudienceSleepWorldMaxPlaceholder;
import fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.audience.AudienceSleepWorldSleepingPlaceholder;
import fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.audience.AudienceWorldTimePlaceholder;
import io.github.miniplaceholders.api.Expansion;
import org.bukkit.entity.Player;

public final class MiniPlaceholdersExpansion extends Placeholders {
	private final Expansion expansion;

	public MiniPlaceholdersExpansion(NightAccelerator plugin) {
		this.expansion = Expansion.builder("nightaccelator")
				.audiencePlaceholder(Player.class, "time", new AudienceWorldTimePlaceholder())
				.audiencePlaceholder(Player.class, "sleeping", new AudienceSleepWorldSleepingPlaceholder(plugin.worldManager()))
				.audiencePlaceholder(Player.class, "max", new AudienceSleepWorldMaxPlaceholder(plugin.worldManager()))
				.build();
	}

	public void register() {
		this.expansion.register();
	}
}
