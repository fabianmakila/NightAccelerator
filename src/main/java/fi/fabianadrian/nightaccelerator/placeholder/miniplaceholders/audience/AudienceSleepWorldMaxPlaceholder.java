package fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.audience;

import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import net.kyori.adventure.text.minimessage.tag.Tag;

public class AudienceSleepWorldMaxPlaceholder extends AudienceSleepWorldPlaceholder {
	public AudienceSleepWorldMaxPlaceholder(WorldManager worldManager) {
		super(worldManager);
	}

	@Override
	protected Tag resolve(SleepWorld world) {
		return Tag.preProcessParsed(String.valueOf(world.max()));
	}
}
