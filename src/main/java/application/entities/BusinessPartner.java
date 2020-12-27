package application.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import proxies.Address;
import proxies.Contacts;
import proxies.Store;


	public class BusinessPartner extends Store implements Serializable{
	 	private static final long serialVersionUID = -7523924559237866260L;
		private String name;
	    private String fullName;
	    private String phone;
	    private List<Contacts> contacts;
	    private List<Store> stores;
	    private Double raiting;  //оценка поставщика
	    public Double getRaiting() {
			return raiting;
		}
		public void setRaiting(Double raiting) {
			this.raiting = raiting;
		}
		public void setContacts(List<Contacts> contacts) {
			this.contacts = contacts;
		}
		public void setStores(List<Store> stores) {
			this.stores = stores;
		}
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
		
		public BusinessPartner(Long id, String name, Address address, Long headQuatersId) {
			super(id, name, address, headQuatersId);
			this.headQuatersId = (long) 0;
		}

		public String toString() {
			return getName().concat(", ").concat(getAddress().getSettlement());
		}
	
}
