package fi.fabianadrian.nightaccelerator.config.section;

public interface TitleSection {
	default boolean enabled() {
		return true;
	}

	default String title() {
		return "<time>";
	}

	default String subtitle() {
		return "<lang:sleep.players_sleeping:'<sleeping>':'<max>'>";
	}
}
