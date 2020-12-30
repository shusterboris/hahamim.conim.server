package application.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@MappedSuperclass
public class Address extends BasicEntity implements Serializable {
	private static final long serialVersionUID = -6276857316780429416L;
	public Long getSettlement() {
		return settlement;
	}

	public void setSettlement(Long settlement) {
		this.settlement = settlement;
	}

	@OneToOne(targetEntity=application.entities.CatItem.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="`settlement`", referencedColumnName="`id`") })	
	@Basic(fetch=FetchType.LAZY)
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

	@Override
	public String toString() {
		return settlement + ", " + streetAddress;
	}

	

}
