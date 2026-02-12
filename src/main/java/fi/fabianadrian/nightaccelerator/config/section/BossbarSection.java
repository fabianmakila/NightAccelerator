package fi.fabianadrian.nightaccelerator.config.section;

import net.kyori.adventure.bossbar.BossBar;

public interface BossbarSection {
	default boolean enabled() {
		return true;
	}

	default String title() {
		return "<lang:sleep.players_sleeping:'<sleeping>':'<max>'>";
	}

	default BossBar.Color color() {
		return BossBar.Color.WHITE;
	}

	default BossBar.Overlay overlay() {
		return BossBar.Overlay.PROGRESS;
	}
}
