package fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.audience;

import fi.fabianadrian.nightaccelerator.placeholder.Placeholders;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import fi.fabianadrian.nightaccelerator.world.WorldManager;
import io.github.miniplaceholders.api.resolver.AudienceTagResolver;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public abstract class AudienceSleepWorldPlaceholder extends Placeholders implements AudienceTagResolver<Player> {
	private final WorldManager worldManager;

	public AudienceSleepWorldPlaceholder(WorldManager worldManager) {
		this.worldManager = worldManager;
	}

	@Override
	public @Nullable Tag tag(Player player, ArgumentQueue queue, @NonNull Context context) {
		SleepWorld world = this.worldManager.world(player.getWorld().getUID());
		if (world == null) {
			return Tag.preProcessParsed("Night acceleration isn't enabled on this world");
		}
		return resolve(world);
	}

	protected abstract Tag resolve(SleepWorld world);
}
