package proxies;

import java.io.Serializable;

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

	public Store(String name, Address address, Long headQuatersId) {
		super();
		this.name = name;
		this.address = address;
		this.headQuatersId = headQuatersId;
	}

	public Store(Long id, String name, Address address, Long headQuatersId) {
		this(name, address, headQuatersId);
		this.id = id;
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
