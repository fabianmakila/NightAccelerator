package fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.audience;

import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.entity.Player;

public final class PlayerWorldTimeResolver extends PlayerWorldResolver {

	public PlayerWorldTimeResolver(WorldManager worldManager) {
		super(worldManager);
	}

	@Override
	protected Tag resolve(SleepWorld world, Player player) {
		return Tag.preProcessParsed(world.time(player.locale()));
	}
}
