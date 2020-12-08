package enums;

public enum ClientStatus {
	POTENTIAL, ACTIVE, FROZEN, SUSPENDED, ARCHIVE;

	public String getMessageKey() {
		return getClass().getSimpleName() + "." + name();
	}

}
