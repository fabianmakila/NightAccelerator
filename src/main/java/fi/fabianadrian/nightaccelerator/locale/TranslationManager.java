package fi.fabianadrian.nightaccelerator.locale;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationStore;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.slf4j.Logger;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class TranslationManager {
	private static final List<Locale> BUNDLED_LOCALES = List.of(Locale.ENGLISH, Locale.of("fi", "FI"));
	private final Logger logger;
	private final TranslationStore.StringBased<MessageFormat> translationStore;

	public TranslationManager(Logger logger) {
		this.logger = logger;

		this.translationStore = TranslationStore.messageFormat(Key.key("nightaccelerator", "main"));

		loadFromResourceBundle();

		GlobalTranslator.translator().addSource(this.translationStore);
	}

	/**
	 * Loads the bundled translations from the jar file.
	 */
	private void loadFromResourceBundle() {
		try {
			BUNDLED_LOCALES.forEach(locale -> {
				ResourceBundle bundle = ResourceBundle.getBundle("messages", locale, UTF8ResourceBundleControl.utf8ResourceBundleControl());
				this.translationStore.registerAll(locale, bundle, false);
			});
		} catch (IllegalArgumentException e) {
			this.logger.warn("Error loading default locale file", e);
		}
	}
}
