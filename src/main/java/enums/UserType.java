package enums;

public enum UserType {
	MEMBER, PARTNER, MODERATOR, SUPERVISOR, STACKHOLDER;

	public String getMessageKey() {
		return getClass().getSimpleName() + "." + name();
	}
}
