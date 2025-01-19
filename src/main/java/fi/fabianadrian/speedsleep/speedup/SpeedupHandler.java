package fi.fabianadrian.speedsleep.speedup;

import fi.fabianadrian.speedsleep.SpeedSleep;
import fi.fabianadrian.speedsleep.config.MainConfig;
import fi.fabianadrian.speedsleep.resolver.SleepTagResolver;
import fi.fabianadrian.speedsleep.speedup.task.AdvanceNightTask;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public final class SpeedupHandler {
	private final SpeedSleep plugin;
	private final BukkitScheduler scheduler;
	private final World world;
	private final List<Player> sleepingPlayers = new ArrayList<>();
	private final List<BukkitTask> tasks = new ArrayList<>();
	private final BossBar bossBar;
	private int totalPlayerCount = 0;
	private final TagResolver.Builder tagResolverBuilder;

	public SpeedupHandler(SpeedSleep plugin, World world) {
		this.plugin = plugin;
		this.scheduler = plugin.getServer().getScheduler();
		this.world = world;

		this.tagResolverBuilder = TagResolver.builder().resolvers(
				Placeholder.unparsed("world_players_sleeping", String.valueOf(this.sleepingPlayers.size())),
				Placeholder.unparsed("world_players_total", String.valueOf(this.totalPlayerCount))
		);

		MainConfig.BossbarConfig bossbarConfig = this.plugin.configManager().mainConfig().bossbar();
		this.bossBar = BossBar.bossBar(Component.empty(), 0.0f, bossbarConfig.color(), BossBar.Overlay.PROGRESS);
	}

	public void handlePlayerLeave(Player player) {
		player.hideBossBar(this.bossBar);
	}

	public void handlePlayerJoin(Player player) {

	}

	public void recalculate() {
		this.scheduler.runTaskLater(this.plugin, this::recalculateTask, 1);
	}

	private void recalculateTask() {
		updatePlayerCounts();

		if (this.sleepingPlayers.isEmpty()) {
			stopTasks();
			return;
		}

		if (!this.tasks.isEmpty()) {
			startTasks();
		}
	}

	private void startTasks() {
		this.world.showBossBar(this.bossBar);
		this.tasks.add(this.scheduler.runTaskTimer(this.plugin, new AdvanceNightTask(this.plugin, this.world, this.sleepingPlayers.size(), this.totalPlayerCount), 0, 1));
		this.tasks.add(this.scheduler.runTaskTimer(this.plugin, this::updateDisplays, 0, 20));
	}

	private void stopTasks() {
		this.world.hideBossBar(this.bossBar);
		this.tasks.forEach(BukkitTask::cancel);
		this.tasks.clear();
	}

	private void updatePlayerCounts() {
		this.totalPlayerCount = 0;
		this.sleepingPlayers.clear();

		for (Player player : this.world.getPlayers()) {
			if (player.isSleepingIgnored() || player.getGameMode() == GameMode.SPECTATOR) {
				return;
			}

			this.totalPlayerCount++;

			if (player.isSleeping()) {
				this.sleepingPlayers.add(player);
			}
		}
	}

	private void updateDisplays() {
		sendSleepingTitle();

	}

	private void updateBossbar() {
		MainConfig.BossbarConfig config = this.plugin.configManager().mainConfig().bossbar();

		Component name = MiniMessage.miniMessage().deserialize(config.title());
	}

	private void sendSleepingTitle() {
		Title.Times times = Title.Times.times(Duration.ZERO, Duration.ofSeconds(1), Duration.ofSeconds(1));
		MainConfig.TitleConfig config = this.plugin.configManager().mainConfig().title();
		this.sleepingPlayers.forEach(player -> {
			TagResolver tagResolver = this.tagResolverBuilder.resolver(SleepTagResolver.worldTime(this.world, player)).build();

			Component title = MiniMessage.miniMessage().deserialize(config.title(), tagResolver);
			Component subtitle = MiniMessage.miniMessage().deserialize(config.subtitle(), tagResolver);
			player.sendTitlePart(TitlePart.TIMES, times);
			player.sendTitlePart(TitlePart.TITLE, title);
			player.sendTitlePart(TitlePart.SUBTITLE, subtitle);
		});
	}
}
