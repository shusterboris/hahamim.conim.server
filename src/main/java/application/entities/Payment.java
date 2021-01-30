package application.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment extends BasicEntity implements Serializable {

	private static final long serialVersionUID = -5594966190833245762L;
	private Float sum;
	private Long member;
	private LocalDate date;
	private String destination;
	private String note;
	private Long purchase;

}