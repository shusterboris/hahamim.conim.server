package application.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "catitems")
public class CatItem extends BasicEntity implements Serializable{
	private static final long serialVersionUID = -9215990634396625375L;
	
	//раздел: регионы, ед измерения, другое
	@Column(name="`itemKey`", nullable=false, length=255)
	private String itemKey;
	
	@Column(name="`language`", nullable=false, length=11)
	private String language;
	
	/**
	 * id of parent item (for items hierarchy)
	 */
	/*
	 * @OneToOne(targetEntity=application.entities.CatItem.class)
	 * 
	 * @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.
	 * LOCK})
	 * 
	 * @JoinColumns({ @JoinColumn(name="`parentId`", referencedColumnName="`id`") })
	 * 
	 * @Basic(fetch=FetchType.LAZY) private CatItem parentId;
	 */
	@Column(name="parentKey", nullable=true, length=255)
	private String parentKey;
	
	@Column(name="`value`", nullable=false, length=255)
	private String value;
	
	@Column(name="`sortOrder`", nullable=false, length=255)
	private Integer sortOrder = 1000;
	/**
	 * additional info, particularly name of image file
	 */
	private String addValue;  

	
	public CatItem(String key, String value, String language, String parentKey) {
		this.itemKey = key;
		this.language = language;
		this.parentKey = parentKey;
		this.value = value;
		this.sortOrder = sortOrder;
	
	}

	public CatItem() {
		
	}

	
	@Override
	public String toString() {
		return this.value;
	}

}
