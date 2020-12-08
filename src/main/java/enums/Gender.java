package enums;

public enum Gender {
	FEMALE, MALE, UNSIGNED;

	public String getMessageKey() {
		return getClass().getSimpleName() + "." + name();
	}
}
