package fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.audience;

import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.entity.Player;

public final class PlayerWorldSleepingResolver extends PlayerWorldResolver {
	public PlayerWorldSleepingResolver(WorldManager worldManager) {
		super(worldManager);
	}

	@Override
	protected Tag resolve(SleepWorld world, Player player) {
		return Tag.preProcessParsed(String.valueOf(world.sleeping().size()));
	}
}
