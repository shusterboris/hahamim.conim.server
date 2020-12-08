package enums;

public enum ProposalStatus {
	INIT, REJECTED, REVIEWED, PUBLISHED, ACCEPTED, SAIL, ARCHIVE;

	public String getMessageKey() {
		return getClass().getSimpleName() + "." + name();
	}
}
