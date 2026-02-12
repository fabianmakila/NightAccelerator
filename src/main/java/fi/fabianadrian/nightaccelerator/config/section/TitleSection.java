package fi.fabianadrian.nightaccelerator.config.section;

import space.arim.dazzleconf.engine.Comments;

public interface TitleSection {
	default boolean enabled() {
		return true;
	}

	@Comments("Available placeholders: <sleeping> <max> <time>")
	default String title() {
		return "<time>";
	}

	@Comments("Available placeholders: <sleeping> <max> <time>")
	default String subtitle() {
		return "<lang:sleep.players_sleeping:'<sleeping>':'<max>'>";
	}
}
