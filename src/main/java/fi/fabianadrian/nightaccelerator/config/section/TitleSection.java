package fi.fabianadrian.nightaccelerator.config.section;

import space.arim.dazzleconf.engine.Comments;

public interface TitleSection {
	default boolean enabled() {
		return true;
	}

	@Comments("Available placeholders: <sleeping> <max> <time>")
	default String sleepingTitle() {
		return "<time>";
	}

	@Comments("Available placeholders: <sleeping> <max> <time>")
	default String sleepingSubtitle() {
		return "<lang:sleep.players_sleeping:'<sleeping>':'<max>'>";
	}

	@Comments("Available placeholders: <sleeping> <max> <time>")
	default String morningTitle() {
		return "<time>";
	}

	@Comments("Available placeholders: <sleeping> <max> <time>")
	default String morningSubtitle() {
		return "Good morning, <name>";
	}
}
