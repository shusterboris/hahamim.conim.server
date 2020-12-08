package proxies;

import java.util.ArrayList;
import java.util.List;

public class BusinessPartner extends Store{
 	private static final long serialVersionUID = -7523924559237866260L;
	private String name;
    private String fullName;
    private String phone;
    private List<Contacts> contacts;
    private List<Store> stores;
 
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<Contacts> getContacts() {
		return contacts;
	}
	public void setContacts(ArrayList<Contacts> contacts) {
		this.contacts = contacts;
	}
	public List<Store> getStores() {
		return stores;
	}
	
	public void setStores(ArrayList<Store> stores) {
		this.stores = stores;
	}
	
	public BusinessPartner(String name, Address address, Long headQuatersId) {
		super(name, address, headQuatersId);
		this.headQuatersId = (long) 0;
	}

	public String toString() {
		return getName().concat(", ").concat(getAddress().getSettlement());
	}
}
