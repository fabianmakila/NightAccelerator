package fi.fabianadrian.nightaccelerator.locale;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore;
import net.kyori.adventure.translation.GlobalTranslator;
import org.slf4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class TranslationManager {
	private static final List<Locale> BUNDLED_LOCALES = List.of(Locale.ENGLISH, Locale.of("fi"));
	private final Logger logger;
	private final MiniMessageTranslationStore store;

	public TranslationManager(Logger logger) {
		this.logger = logger;
		this.store = MiniMessageTranslationStore.create(Key.key("nightaccelerator", "main"));
	}

	public void load() {
		try {
			BUNDLED_LOCALES.forEach(locale -> {
				ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
				this.store.registerAll(locale, bundle, false);
			});
		} catch (IllegalArgumentException e) {
			this.logger.warn("Error loading default locale file", e);
		}
		GlobalTranslator.translator().addSource(this.store);
	}
}
