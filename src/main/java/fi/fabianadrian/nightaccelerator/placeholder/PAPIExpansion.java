package fi.fabianadrian.nightaccelerator.placeholder;

import fi.fabianadrian.nightaccelerator.NightAccelerator;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public final class PAPIExpansion extends PlaceholderExpansion {
	private final WorldManager worldManager;

	public PAPIExpansion(NightAccelerator plugin) {
		this.worldManager = plugin.worldManager();
	}

	@Override
	public @NotNull String getIdentifier() {
		return "nightaccelerator";
	}

	@Override
	public @NotNull String getAuthor() {
		return "FabianAdrian";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0.0";
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
		if (player == null) {
			return null;
		}

		SleepWorld world = this.worldManager.world(player);
		if (world == null) {
			return "Night acceleration isn't enabled on this world";
		}

		switch (params.toLowerCase(Locale.ROOT)) {
			case "sleeping" -> {
				return String.valueOf(world.sleeping().size());
			}
			case "max" -> {
				return String.valueOf(world.max());
			}
			case "time" -> {
				return world.time(player.locale());
			}
		}
		return null;
	}
}
