package fi.fabianadrian.speedsleep.speedup;

import fi.fabianadrian.speedsleep.SpeedSleep;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class WorldManager {
	private final Map<UUID, SpeedupHandler> worldData = new HashMap<>();

	public WorldManager(SpeedSleep plugin) {
		List<String> disabledWorldNames = plugin.config().disabledWorldNames();
		plugin.getServer().getWorlds().forEach(world -> {
			if (!world.isBedWorks()) {
				return;
			}

			if (disabledWorldNames.contains(world.getName())) {
				return;
			}

			this.worldData.put(world.getUID(), new SpeedupHandler(plugin, world));
		});
	}

	public void recalculate(UUID worldUUID) {
		SpeedupHandler data = this.worldData.get(worldUUID);
		if (data == null) {
			return;
		}
		data.recalculate();
	}
}
