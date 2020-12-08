package enums;

public enum SuggestionType {
	TENDER, ACTION;

	public String getMessageKey() {
		return getClass().getSimpleName() + "." + name();
	}
}
