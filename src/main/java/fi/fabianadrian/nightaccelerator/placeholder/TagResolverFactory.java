package fi.fabianadrian.nightaccelerator.placeholder;

import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

public class TagResolverFactory extends Placeholders {
	protected TagResolver.Builder globalBuilder(SleepWorld world) {
		return TagResolver.builder().resolvers(
				Placeholder.unparsed("sleeping", String.valueOf(sleeping(world))),
				Placeholder.unparsed("max", String.valueOf(max(world)))
		);
	}

	protected TagResolver.Builder playerBuilder(SleepWorld world, Player player) {
		return globalBuilder(world).resolver(
				Placeholder.unparsed("time", time(player))
		);
	}

	public TagResolver player(SleepWorld world, Player player) {
		return this.playerBuilder(world, player).build();
	}

	public TagResolver global(SleepWorld world) {
		return this.globalBuilder(world).build();
	}
}
