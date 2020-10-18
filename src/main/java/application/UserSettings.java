package application;

public class UserSettings {
	private static String choosenLanguage = "RU";

	public static String getChoosenLanguage() {
		return choosenLanguage;
	}

	public static void setChoosenLanguage(String choosenLanguage) {
		UserSettings.choosenLanguage = choosenLanguage;
	}

}
