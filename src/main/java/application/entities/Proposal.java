package application.entities;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
/**
 * Тендерное предложение (предложение на совместную закупку) или акция
 * 
 * @author Одиссей
 *
 */
@Getter
@Setter
@Entity
@Table(name = "actions")
public class Proposal extends BasicEntity implements Serializable{
	private static final long serialVersionUID = 985468545500612436L;
	private String name;
	/**
	 * base price (non actions) from proposals for actions and initial tender's price for tender
	 */
	//категория товара
	private String category; 
	private String region;
	private Long initiator;
	private Float price;//розничная цена 
	/**
	 * lowest price from proposals for actions and final (best price proposal) tender's price for tender
	 */	
	private Float lastPrice;
	private String measure;
	private Float threshold; //минимальная закупка
	private Float thresholdmax;//верхний предел закупки
	private Integer status; // Из набора TenderStatus
	private Float total = (float) 0.0;
	private String description;
	private LocalDate dueDate;// срок окончания приема заявок
	private LocalDate publicationDate; private LocalDate dateOfSailStarting;
	private LocalDate closeDate;
	private Long bundle; //общая закупка

	/*
	 * @OneToOne(targetEntity=application.entities.BusinessPartner.class)
	 * 	 * @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.	 * LOCK})
	 *	 * @JoinColumns({ @JoinColumn(name="`supplier`", referencedColumnName="`Id`") })
	 * 	 * @Basic(fetch=FetchType.LAZY) private BusinessPartner supplier;
	 */
	private Long supplier;


	@OneToMany(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = "proposal")
	private Set<AppImage> photos ;
	
	//@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JoinColumn(name="id") 
	// private Set<Comment> comments = new HashSet<>();
	@OneToMany(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = "proposal")

	private Set<PriceProposal> priceProposals;
		/*
	 * @OneToMany(targetEntity=application.entities.Store.class)
	 * 
	 * @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.
	 * LOCK}) //@JoinColumns({ @JoinColumn(name="`stores`",
	 * referencedColumnName="`Id`") }) //@Basic(fetch=FetchType.LAZY)
	 * 
	 * @JoinColumn(name="`proposalid`", nullable=true)
	 * 
	 * @org.hibernate.annotations.LazyCollection(org.hibernate.annotations.
	 * LazyCollectionOption.TRUE) private List<Store> stores;
	 */
	
}
