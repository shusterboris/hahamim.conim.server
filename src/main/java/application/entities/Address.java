package application.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Address extends BasicEntity implements Serializable {
	private static final long serialVersionUID = -6276857316780429416L;

	protected Long settlement;  
	
	@Column(name="`streetAddress`", nullable=true, length=255)	
	protected String streetAddress;
	
	protected Float latitude;
	protected Float altitude;

	
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

	public Long getSettlement() {
		return settlement;
	}

	public void setSettlement(Long settlement) {
		this.settlement = settlement;
	}

	
}
