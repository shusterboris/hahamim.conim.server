package application.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import enums.ProposalStatus;
import proxies.Address;
import proxies.PriceProposal;

/**
 * Тендерное предложение (предложение на совместную закупку)
 * 
 * @author Одиссей
 *
 */
@Entity
@Table(name = "actions")
public class Proposal extends BasicEntity {
	
	 private String name;
	/**
	 * base price (non actions) from proposals for actions and initial tender's price for tender
	 */
	//категория товара
	 private Long category; 
	 private Long region;
	 private Member initiator;
	 private Float price;//розничная цена 
	/**
	 * lowest price from proposals for actions and final (best price proposal) tender's price for tender
	 */	
	private Float lastPrice;
	private LocalDateTime dueDate;// срок окончания приема заявок
	private Long measure;
	private Float threshold; //минимальная закупка
	private Float thresholdmax;//верхний предел закупки
	private Integer status; // Из набора TenderStatus
    private Long photos;
	private LocalDateTime publicationDate;
	private Long supplierId;
	private Float total = (float) 0.0;
	private LocalDateTime dateOfSailStarting;
	private LocalDateTime closeDate;
	private String description;
	private List<PriceProposal> priceProposals;
	private List<Address> stores;
	private Long bundle; //общая закупка
	public Person getAuthor() {
		return initiator;
	}

	public void setAuthor(Member author) {
		this.initiator = author;
	}

	public Member getInitiator() {
		return initiator;
	}

	public void setInitiator(Member initiator) {
		this.initiator = initiator;
	}

	
	public Float getMaxPrice() {
		return price;
	}

	public void setMaxPrice(Float maxPrice) {
		this.price = maxPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Float getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Float lastPrice) {
		this.lastPrice = lastPrice;
	}


	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Исключительно для отладки
	 * 
	 * @param id         - уникальный id для таблицы
	 * @param item       - название товара на избранном языке
	 * @param categories - категория из справочника
	 * @param region     - регион из справочника
	 * @param author     - инициатор тендера, Person
	 * @param i          - максимальная цена, по которой участник приобретет товар
	 * @param dueDate    - дата завершения тендера
	 */
	
	@Override
	public String toString() {
		return name;
	}

}
