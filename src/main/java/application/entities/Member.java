package application.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "`member`")
public class Member extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1320062617706369358L;

	@Column(name = "`level`", nullable = false, length = 11)
	private Integer level; // member level
	private Long region;
	private Long partner;
	@Column(name = "`lastname`", nullable = true, length = 255)
	private String lastName;
	@Column(name = "`firstname`", nullable = false, length = 255)
	private String firstName;
	@Column(name = "`gender`", nullable = true, length = 11)
	private Integer gender;
	@Column(name = "`birthday`", nullable = true)
	private LocalDate birthday;
	@Column(name = "`phone`", nullable = true, length = 255)
	private String phone;
	@Column(name = "`email`", nullable = true, length = 255)
	private String email;
	@Column(name = "`rate`", columnDefinition = "Decimal(10,2) default '2.00'")
	private Float rate;
	@Column(name = "`note`", nullable = true, length = 255)
	private String note;
	@Column(name = "`login`", nullable = true, length = 255)
	private String login;
	@Column(name = "`password`", nullable = true, length = 255)
	private String password;
	@Column(name = "`status`", nullable = false, length = 11)
	private Integer status = 0;
	@Column(name = "`type`", nullable = false, length = 11)
	private Integer type = 0;
	@Transient
	private String autorityList;
	private String telegram;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
	private Set<Delivery> delivery;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "member")
	private Set<BusinessPartner> partners = new HashSet<>();
}
