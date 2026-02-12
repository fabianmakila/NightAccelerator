package fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders;

import fi.fabianadrian.nightaccelerator.placeholder.TagResolverFactory;
import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

public final class MiniPlaceholdersTagResolverFactory extends TagResolverFactory {
	@Override
	public TagResolver player(SleepWorld world, Player player) {
		return super.playerBuilder(world, player).resolver(MiniPlaceholders.audienceGlobalPlaceholders()).build();
	}

	@Override
	public TagResolver global(SleepWorld world) {
		return super.globalBuilder(world).resolver(MiniPlaceholders.globalPlaceholders()).build();
	}
}
