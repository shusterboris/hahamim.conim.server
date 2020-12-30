package application.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "businesspartner")
public class BusinessPartner extends Store implements Serializable{
 	private static final long serialVersionUID = -7523924559237866260L;
 	@Column(name="`fullname`", nullable=false, length=255)
 	private String fullName;
    private boolean supplier = false;
    //private List<Member> contacts;
    private Double raiting;  //оценка поставщика
    
    public Double getRaiting() {
		return raiting;
	}
	public void setRaiting(Double raiting) {
		this.raiting = raiting;
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
	

	public BusinessPartner(String name, String fname, String addr) {
		super( name, addr, (long)0);
		this.fullName=fname;
		this.name=name;
	}

	public boolean isSupplier() {
		return supplier;
	}
	public void setSupplier(boolean supplier) {
		this.supplier = supplier;
	}
	
}
