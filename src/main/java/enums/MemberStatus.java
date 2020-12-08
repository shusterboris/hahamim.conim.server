package enums;

public enum MemberStatus {
	SIMPLE, GOLD, PLATINUM;

	public String getMessageKey() {
		return getClass().getSimpleName() + "." + name();
	}
}
