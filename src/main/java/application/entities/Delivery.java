package application.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "`delivery`")
public class Delivery extends Address implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3916108119385178232L;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	public Delivery() {
		super();
		// TODO Auto-generated constructor stub
	}
}
