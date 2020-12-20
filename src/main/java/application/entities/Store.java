package application.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "addresses")
public class Store extends BasicEntity implements Serializable{
	private static final long serialVersionUID = 2624926622636823053L;
	protected String name;
	protected Address address;
	protected Long headQuatersId = (long) 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Long getHeadQuatersId() {
		return headQuatersId;
	}

	public void setHeadQuatersId(Long headQuatersId) {
		this.headQuatersId = headQuatersId;
	}


	public Store(Long id, String name, Address address, Long headQuatersId) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.headQuatersId = headQuatersId;
	}

	public String toStringShort() {
		if (!"".equals(name))
			return name.concat(". ").concat(getAddress().getSettlement());
		else
			return getAddress().getSettlement().concat(",").concat(getAddress().getStreetAddress());
	}
	
	public String toString() {
		if (!"".equals(name))
			return name.concat(". ").concat(getAddress().getSettlement()).concat(",").concat(getAddress().getStreetAddress());
		else
			return getAddress().getSettlement().concat(",").concat(getAddress().getStreetAddress());
		
	}
}
