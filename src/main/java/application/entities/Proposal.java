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
public class Proposal extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 985468545500612436L;
	private String name;
	private String category;
	private String region;
	private Long initiator;
	private Float price = (float) 0;;// розничная цена
	/**
	 * lowest price from proposals for actions and final (best price proposal)
	 * tender's price for tender
	 */
	private Float lastPrice = (float) 0;
	private String measure;
	private Float threshold; // минимальная закупка
	private Float thresholdmax;// верхний предел закупки
	private Integer status; // Из набора TenderStatus
	private Float total = (float) 0.0;
	private Float quantity = (float) 0.0;
	private String description;
	private LocalDate dueDate;// срок окончания приема заявок
	private LocalDate publicationDate;
	private LocalDate dateOfSailStarting;
	private LocalDate closeDate;
	private Long bundle; // общая закупка

	private Long supplier;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "proposal")
	private Set<AppImage> photos;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "proposal")

	private Set<PriceProposal> priceProposals;
	private Boolean intOnly = true;

}
