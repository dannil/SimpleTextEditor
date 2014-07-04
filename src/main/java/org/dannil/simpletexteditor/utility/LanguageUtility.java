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
		System.out.println("Running default...");
		Locale displayLanguage = Locale.getDefault();
		if (availableLanguages.contains(displayLanguage)) {
			System.out.println("Language detected: " + displayLanguage.toString());
			return ResourceBundle.getBundle(LANGUAGE_PATH, displayLanguage);
		}
		return ResourceBundle.getBundle(LANGUAGE_PATH, new Locale("en", "US"));
	}
	
	/**
	 * Utility method for retrieving keys from language files with a null check.
	 * If the key can't be found, it will instead retrieve the translation from
	 * a failover language (i.e. English) to prevent exceptions.
	 * @param key The key which we will fetch the data from
	 * @param bundle The ResourceBundle we want to fetch from
	 * @return String with the desired language or failover language
	 */
	public static String getStringWithCheck(String key, ResourceBundle bundle) {
		boolean keyExists = bundle.containsKey(key);
		if (keyExists) {
			return bundle.getString(key);
		}
		else {
			System.out.println("Using failover language for property " + key);
			return ResourceBundle.getBundle(LANGUAGE_PATH, new Locale("en", "US")).getString(key);
		}
	}
}
