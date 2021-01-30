package application.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stores")
public class Store extends Address implements Serializable{
	private static final long serialVersionUID = 2624926622636823053L;
	@Column(name="`name`", nullable=true, length=255)	
	protected String name;

	protected Long headQuaters = null;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "bp_id", nullable = false)
	private BusinessPartner bp;



	public Store(String name, Address address, Long headQuaters) {
	
		this.name = name;
		this.settlement = address.getSettlement();
		this.streetAddress= address.getStreetAddress();
		this.latitude= address.getLatitude();
		this.altitude= address.getAltitude();
		this.headQuaters = headQuaters;
	}

	public Store(String name, String addr, Long headQuaters) {
		
		this.name = name;
		this.streetAddress= addr;
		this.headQuaters = headQuaters;
	}

	public Store() {
		super();
	}

	/*
	 * public String toStringShort() { if (!"".equals(name)) return
	 * name.concat(". ").concat(getSettlement()); else return
	 * getSettlement().concat(",").concat(getAddress().getStreetAddress()); }
	 * 
	 * public String toString() { if (!"".equals(name)) return
	 * name.concat(". ").concat(getAddress().getSettlement()).concat(",").concat(
	 * getAddress().getStreetAddress()); else return
	 * getAddress().getSettlement().concat(",").concat(getAddress().getStreetAddress
	 * ());
	 * 
	 * }
	 */
}
