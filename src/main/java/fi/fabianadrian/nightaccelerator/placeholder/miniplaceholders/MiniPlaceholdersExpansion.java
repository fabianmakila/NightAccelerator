package fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.audience.PlayerWorldMaxResolver;
import fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.audience.PlayerWorldSleepingResolver;
import fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.audience.PlayerWorldTimeResolver;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import io.github.miniplaceholders.api.Expansion;
import org.bukkit.entity.Player;

public final class MiniPlaceholdersExpansion {
	private final Expansion expansion;

	public MiniPlaceholdersExpansion(NightAccelerator plugin) {
		WorldManager manager = plugin.worldManager();
		this.expansion = Expansion.builder("nightaccelator")
				.author("FabianAdrian")
				.version("1.0.0")
				.audiencePlaceholder(Player.class, "time", new PlayerWorldTimeResolver(manager))
				.audiencePlaceholder(Player.class, "sleeping", new PlayerWorldSleepingResolver(manager))
				.audiencePlaceholder(Player.class, "max", new PlayerWorldMaxResolver(manager))
				.build();
	}

	public void register() {
		this.expansion.register();
	}
}
