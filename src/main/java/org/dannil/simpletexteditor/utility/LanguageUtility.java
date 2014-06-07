package org.dannil.simpletexteditor.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class LanguageUtility {
	private static String LANGUAGE_PATH = "languages/language";
	
	private static List<Locale> availableLanguages;
	
	static {
		System.out.println("Initializing languages...");
		availableLanguages = new ArrayList<Locale>();
		availableLanguages.add(new Locale("en", "US"));
		availableLanguages.add(new Locale("sv", "SE"));
		System.out.println("Done.");
	}
	
	/**
	 * Returns a ResourceBundle with the language which matches the users current display language.
	 * If the language file doesn't exist, return a standard language (enUS)
	 * @return ResourceBundle with appropriate language
	 */
	public static ResourceBundle getDefault() {
		Locale displayLanguage = Locale.getDefault();
		/*if (availableLanguages.contains(displayLanguage)) {
			return ResourceBundle.getBundle(LANGUAGE_PATH, displayLanguage);
		}*/
		return ResourceBundle.getBundle(LANGUAGE_PATH, new Locale("en", "US"));
	}
}
