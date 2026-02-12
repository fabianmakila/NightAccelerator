package fi.fabianadrian.nightaccelerator.config.section;

import net.kyori.adventure.bossbar.BossBar;
import space.arim.dazzleconf.engine.Comments;

public interface BossbarSection {
	default boolean enabled() {
		return true;
	}

	@Comments("Available placeholders: <sleeping> <max> <time>")
	default String title() {
		return "<lang:sleep.players_sleeping:'<sleeping>':'<max>'>";
	}

	@Comments("Possible values: PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE")
	default BossBar.Color color() {
		return BossBar.Color.WHITE;
	}

	@Comments("Possible values: PROGRESS, NOTCHED_6, NOTCHED_10, NOTCHED_12, NOTCHED_20")
	default BossBar.Overlay overlay() {
		return BossBar.Overlay.PROGRESS;
	}
}
