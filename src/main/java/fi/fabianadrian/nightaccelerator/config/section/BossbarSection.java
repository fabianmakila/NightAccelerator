package fi.fabianadrian.nightaccelerator.config.section;

import net.kyori.adventure.bossbar.BossBar;

public interface BossbarSection {
	default String title() {
		return "<translatable:sleep.players_sleeping:'<world_players_sleeping>':'<world_players_total>'>";
	}

	default BossBar.Color color() {
		return BossBar.Color.BLUE;
	}
}
