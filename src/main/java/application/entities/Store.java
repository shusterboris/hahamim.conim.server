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
public class Store extends Address implements Serializable{
	private static final long serialVersionUID = 2624926622636823053L;
	@Column(name="`name`", nullable=true, length=255)	
	protected String name;
	
	@OneToOne(targetEntity=application.entities.Store.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="`headQuatersId`", referencedColumnName="`id`") })	
	@Basic(fetch=FetchType.LAZY)		
	protected Long headQuatersId = (long) 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getHeadQuatersId() {
		return headQuatersId;
	}

	public void setHeadQuatersId(Long headQuatersId) {
		this.headQuatersId = headQuatersId;
	}


	public Store(String name, Address address, Long headQuatersId) {
	
		this.name = name;
		this.settlement = address.getSettlement();
		this.streetAddress= address.getStreetAddress();
		this.latitude= address.getLatitude();
		this.altitude= address.getAltitude();
		this.headQuatersId = headQuatersId;
	}
	public Store(String name, String addr, Long headQuatersId) {
		
		this.name = name;
		this.streetAddress= addr;
		
		this.headQuatersId = headQuatersId;
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
