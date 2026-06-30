package fi.fabianadrian.nightaccelerator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.permission.Permission;
import org.bukkit.command.CommandSender;

@Command("nightaccelerator")
public final class NightAcceleratorCommand {
	private static final Component COMPONENT_PREFIX = MiniMessage.miniMessage().deserialize(
			"[NightAccelerator] "
	);
	private static final Component COMPONENT_RELOAD_SUCCESS = Component.textOfChildren(
			COMPONENT_PREFIX,
			Component.translatable("nightaccelerator.command.reload.success")
	);
	private static final Component COMPONENT_RELOAD_FAILURE = Component.textOfChildren(
			COMPONENT_PREFIX,
			Component.translatable("nightaccelerator.command.reload.failure")
	);
	private final NightAccelerator plugin;

	public NightAcceleratorCommand(NightAccelerator plugin) {
		this.plugin = plugin;
	}

	@Executes("reload")
	@Permission("nightaccelerator.command.reload")
	void onReload(CommandSender sender) {
		try {
			this.plugin.load();
			sender.sendMessage(COMPONENT_RELOAD_SUCCESS);
		} catch (Throwable e) {
			this.plugin.getSLF4JLogger().error("Couldn't reload plugin", e);
			sender.sendMessage(COMPONENT_RELOAD_FAILURE);
		}
	}
}
