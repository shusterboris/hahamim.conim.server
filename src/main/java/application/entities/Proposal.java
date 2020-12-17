package application.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "actions")
public class Proposal extends BasicEntity{
	private String name;
	/**
	 * base price (non actions) from proposals for actions and initial tender's price for tender
	 */
	private Float price;
	/**
	 * lowest price from proposals for actions and final (best price proposal) tender's price for tender
	 */	
	private Float lastPrice;
	private LocalDate dueDate;// срок окончания приема заявок
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(Float lastPrice) {
		this.lastPrice = lastPrice;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
	
}
