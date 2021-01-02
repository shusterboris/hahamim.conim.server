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

@Entity
@Table(name = "catitems")
public class CatItem extends BasicEntity implements Serializable{
	private static final long serialVersionUID = -9215990634396625375L;
	//уникальный для справочника, общий для языковых вариантов темы
	@Column(name="`itemId`", nullable=false, length=11)
	private Long itemId;
	//раздел: регионы, ед измерения, другое
	@Column(name="`itemKey`", nullable=false, length=255)
	private String itemKey;
	
	@Column(name="`language`", nullable=false, length=11)
	private String language;
	
	/**
	 * id of parent item (for items hierarchy)
	 */
	@OneToOne(targetEntity=application.entities.CatItem.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="`parentId`", referencedColumnName="`id`") })	
	@Basic(fetch=FetchType.LAZY)	
	private Long parentId = (long) 0;
	
	@Column(name="`value`", nullable=false, length=255)
	private String value;
	
	@Column(name="`sortOrder`", nullable=false, length=255)
	private Integer sortOrder = 1000;
	/**
	 * additional info, particularly name of image file
	 */
	private String addValue;  

	public String getAddValue() {
		return addValue;
	}

	public void setAddValue(String addValue) {
		this.addValue = addValue;
	}

	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(String key) {
		this.itemKey = key;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public CatItem(String key, String language, Long parentId, String value, Integer sortOrder) {
		this.itemKey = key;
		this.language = language;
		this.parentId = parentId;
		this.value = value;
		this.sortOrder = sortOrder;
	}

	

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public CatItem(String key, String value, String language, long parentItemId) {
		this(key, language, (long) 0, value, 0);
	
	}

	@Override
	public String toString() {
		return this.value;
	}

}
