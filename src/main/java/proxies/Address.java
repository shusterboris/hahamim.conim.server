package proxies;

public class Address extends BasicEntity {
	private static final long serialVersionUID = -6276857316780429416L;
	private String region;  //from catItem
	private String settlement;  //from catItem
	private String streetAddress;
	private Float latitude;
	private Float altitude;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlment) {
		this.settlement = settlment;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getAltitude() {
		return altitude;
	}

	public void setAltitude(Float altitude) {
		this.altitude = altitude;
	}

	@Override
	public String toString() {
		return region + ", " + settlement + ", " + streetAddress;
	}

	

}
