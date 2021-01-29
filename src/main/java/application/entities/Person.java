 package application.entities;

import java.io.Serializable;

import javax.persistence.Column;


public class Person extends BasicEntity implements Serializable{
	private static final long serialVersionUID = -2772623296089653648L;
	@Column(name="`lastname`", nullable=false, length=255)
	private String lastName;
	@Column(name="`firstname`", nullable=false, length=255)
	private String firstName;
	@Column(name="`gender`", nullable=true, length=11)
	private Integer gender;
	@Column(name="`birthday`", nullable=true)	
	private java.util.Date birthday;
	@Column(name="`phone`", nullable=true, length=255)
	private String phone;
	@Column(name="`email`", nullable=true, length=255)
	private String email;
	//private String tag;
	@Column(name="`note`", nullable=true, length=255)
	private String note;
	@Column(name="`login`", nullable=true, length=255)
	private String login;
	@Column(name="`password`", nullable=true, length=255)
	private String password;
    //private ClientStatus status = ClientStatus.POTENTIAL;
	//private UserType userType = UserType.MEMBER;
	@Column(name="`status`", nullable=false, length=11)
	private Integer status=0;
	@Column(name="`type`", nullable=false, length=11)
	private Integer type=0;
	

	public String getLastName() {
		return lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	

	public String toString() {
		return getLastName().concat(" ").concat(getFirstName());
	}

	

}
