package fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.audience;

import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import net.kyori.adventure.text.minimessage.tag.Tag;

public class AudienceSleepWorldSleepingPlaceholder extends AudienceSleepWorldPlaceholder {
	public AudienceSleepWorldSleepingPlaceholder(WorldManager worldManager) {
		super(worldManager);
	}

	@Override
	protected Tag resolve(SleepWorld world) {
		return Tag.preProcessParsed(String.valueOf(world.sleeping().size()));
	}
}
