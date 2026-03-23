package fi.fabianadrian.nightaccelerator.world;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

public final class WorldManager {
	private final NightAccelerator plugin;
	private final Map<UUID, SleepWorld> worlds = new HashMap<>();
	private final Set<UUID> recalculationsQueued = new HashSet<>();

	public WorldManager(NightAccelerator plugin) {
		this.plugin = plugin;
	}

	public void load() {
		this.worlds.values().forEach(SleepWorld::shutdown);
		this.worlds.clear();
		this.recalculationsQueued.clear();

		List<String> enabledWorlds = this.plugin.config().enabledWorlds();
		enabledWorlds.forEach(worldName -> {
			World world = Bukkit.getWorld(worldName);
			if (world == null) {
				this.plugin.getSLF4JLogger().warn("Please check configuration! Couldn't find a world named {}", worldName);
				return;
			}
			SleepWorld sleepWorld = new SleepWorld(this.plugin, world);
			sleepWorld.recalculate();
			this.worlds.put(world.getUID(), sleepWorld);
		});
	}

	public SleepWorld world(World world) {
		return this.worlds.get(world.getUID());
	}

	public SleepWorld world(Player player) {
		return world(player.getWorld());
	}

	public void recalculate(World world) {
		SleepWorld sleepWorld = this.worlds.get(world.getUID());
		if (sleepWorld == null) {
			return;
		}

		if (!this.recalculationsQueued.add(world.getUID())) {
			return;
		}

		Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
			sleepWorld.recalculate();
			this.recalculationsQueued.remove(world.getUID());
		}, 1);
	}
}
