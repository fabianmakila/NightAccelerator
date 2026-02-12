package fi.fabianadrian.nightaccelerator.config.section;

import net.kyori.adventure.bossbar.BossBar;

public interface BossbarSection {
	default boolean enabled() {
		return true;
	}

	default String title() {
		return "<translatable:sleep.players_sleeping:'<world_players_sleeping>':'<world_players_total>'>";
	}

	default BossBar.Color color() {
		return BossBar.Color.WHITE;
	}

	default BossBar.Overlay overlay() {
		return BossBar.Overlay.PROGRESS;
	}
}
