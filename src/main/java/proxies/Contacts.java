package proxies;
// used only for BusinessPartner
public class Contacts {
  private String firstName;
  private String lastName;
  private String phone;
  
  
public String getFirstName() {
	return firstName;
}


public void setFirstName(String firstName) {
	this.firstName = firstName;
}


public String getLastName() {
	return lastName;
}


public void setLastName(String name) {
	this.lastName = name;
}


public String getPhone() {
	return phone;
}


public void setPhone(String phone) {
	this.phone = phone;
}


public Contacts(String firstName, String name, String phone) {
	this.firstName = firstName;
	this.lastName = name;
	this.phone = phone;
}
}
