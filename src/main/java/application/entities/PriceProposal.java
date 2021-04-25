package application.entities;

import java.io.Serializable;

import javax.persistence.Column;
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
@Table(name = "priceproposals")
public class PriceProposal extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 7882895218501386694L;

	private long member;
	@Column(name = "`priceLevel`", nullable = false, length = 11)
	private Integer priceLevel = 1;
	@Column(name = "`quantity`", nullable = false, length = 11)
	private Float quantity = (float) 0;
	@Column(name = "`price`", nullable = false, length = 11)
	private Float price = (float) 0;
	@Column(name = "`proposalType`", nullable = false, length = 11)
	private int proposalType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proposal_id", nullable = false)
	private Proposal proposal;
	private String delivery;
	@Column(length = 25)
	private String orderId;
	private boolean sent;
	private Float amount = (float) 0;
	private Integer status = 0; // 0 - комплектуется, 9 - получена
}
