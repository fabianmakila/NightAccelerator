package fi.fabianadrian.nightaccelerator.placeholder.miniplaceholders.audience;

import fi.fabianadrian.nightaccelerator.placeholder.Placeholders;
import io.github.miniplaceholders.api.resolver.AudienceTagResolver;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

public final class AudienceWorldTimePlaceholder extends Placeholders implements AudienceTagResolver<Player> {
	@Override
	public @Nullable Tag tag(Player player, ArgumentQueue queue, Context ctx) {
		return Tag.preProcessParsed(time(player));
	}
}
