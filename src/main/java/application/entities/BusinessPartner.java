package application.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "businesspartner")
//public class BusinessPartner extends Store implements Serializable{
public class BusinessPartner extends BasicEntity implements Serializable{
	private static final long serialVersionUID = -7523924559237866260L;
	@Column(name="`fullname`", nullable=false, length=255)
	private String fullName;
	private boolean supplier = false;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bp")
	private Set<Store> stores;

	private Double raiting;  //оценка поставщика
	/*
	 * public BusinessPartner(String name, String fname, String addr) { super( name,
	 * addr, null); this.fullName=fname; this.name=name; }
	 * 
	 * public BusinessPartner() { super( "", "", null); }
	 */
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "partn_memb", joinColumns = { @JoinColumn(name = "partn_id") }, inverseJoinColumns = {
			@JoinColumn(name = "memb_id") })
	private Set<Member> member = new HashSet<>();
}
