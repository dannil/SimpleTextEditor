package org.dannil.simpletexteditor.utility;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public final class LanguageUtility {
	private static String LANGUAGE_PATH = "/languages";
	
	private static Locale[] availableLanguages = { /*new Locale("sv", "SE"),*/ new Locale("en", "US") };
	
	/**
	 * Returns a ResourceBundle with the language which matches the users current display language.
	 * If the language file doesn't exist, return a standard language (enUS)
	 * @return ResourceBundle with appropriate language
	 */
	public static ResourceBundle getDefault() {
		Locale displayLanguage = Locale.getDefault();
		/*if (Arrays.asList(availableLanguages).contains(displayLanguage)) {
			return ResourceBundle.getBundle(LANGUAGE_PATH, displayLanguage);
		}*/
		return ResourceBundle.getBundle(LANGUAGE_PATH, new Locale("en", "US"));
	}
}
