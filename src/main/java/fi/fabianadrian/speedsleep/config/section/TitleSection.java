package fi.fabianadrian.speedsleep.config.section;

public interface TitleSection {
	default String title() {
		return "<time>";
	}

	default String subtitle() {
		return "<translatable:sleep.players_sleeping:'<world_players_sleeping>':'<world_players_total>'>";
	}
}
