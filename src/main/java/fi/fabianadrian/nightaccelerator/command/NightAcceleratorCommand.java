package fi.fabianadrian.nightaccelerator.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fi.fabianadrian.nightaccelerator.NightAccelerator;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;

import static io.papermc.paper.command.brigadier.Commands.literal;

public final class NightAcceleratorCommand {
	private static final Component COMPONENT_PREFIX = MiniMessage.miniMessage().deserialize(
			"<#111827>[<#60a5fa>NightAccelerator</#60a5fa>]</#111827> "
	);
	private static final Component COMPONENT_RELOAD_SUCCESS = COMPONENT_PREFIX.append(Component.translatable(
			"nightaccelerator.command.reload.success", NamedTextColor.GREEN
	));
	private static final Component COMPONENT_RELOAD_FAILURE = COMPONENT_PREFIX.append(Component.translatable(
			"nightaccelerator.command.reload.failure", NamedTextColor.RED
	));
	private static final String PERMISSION_RELOAD = "nightaccelerator.command.reload";
	private final NightAccelerator plugin;
	private final LifecycleEventManager<Plugin> manager;

	public NightAcceleratorCommand(NightAccelerator plugin) {
		this.plugin = plugin;
		this.manager = plugin.getLifecycleManager();
	}

	public void register() {
		LiteralArgumentBuilder<CommandSourceStack> rootBuilder = literal("nightaccelerator")
				.requires(stack -> stack.getSender().hasPermission(PERMISSION_RELOAD));

		LiteralCommandNode<CommandSourceStack> reloadNode = rootBuilder.then(literal("reload")
				.executes(this::executeReload)
		).build();

		this.manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();
			commands.register(reloadNode);
		});
	}

	private int executeReload(CommandContext<CommandSourceStack> ctx) {
		try {
			this.plugin.load();
			ctx.getSource().getSender().sendMessage(COMPONENT_RELOAD_SUCCESS);
		} catch (Throwable e) {
			ctx.getSource().getSender().sendMessage(COMPONENT_RELOAD_FAILURE);
		}

		return Command.SINGLE_SUCCESS;
	}
}
