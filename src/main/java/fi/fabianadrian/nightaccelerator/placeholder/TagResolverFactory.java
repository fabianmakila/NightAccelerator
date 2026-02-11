package fi.fabianadrian.nightaccelerator.placeholder;

import fi.fabianadrian.nightaccelerator.world.SleepWorld;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

public class TagResolverFactory extends Placeholders {
	public TagResolver resolver(SleepWorld world, Player player) {
		return TagResolver.resolver(
				Placeholder.unparsed("time", time(player)),
				Placeholder.unparsed("sleeping", String.valueOf(sleeping(world))),
				Placeholder.unparsed("max", String.valueOf(max(world)))
		);
	}
}
