package application.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="`member`")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Member")
@SuppressWarnings({ "all", "unchecked" })
public class Member extends Person implements Serializable{
	public Member() {
		
	}
	private static final long serialVersionUID = 1320062617706369358L;
	
	@Column(name="`level`", nullable=false, length=11)	
	private Integer level; //member level
	
	@OneToOne(targetEntity=application.entities.CatItem.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="`region`", referencedColumnName="`id`") })	
	@Basic(fetch=FetchType.LAZY)
	private Long region;
	
	@ManyToOne(targetEntity=application.entities.BusinessPartner.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="`partnerId`", referencedColumnName="`id`") })	
	@Basic(fetch=FetchType.LAZY)
	private Long partnerId;
	
	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	

}
