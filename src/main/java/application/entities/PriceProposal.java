package application.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import enums.PriceProposalType;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "priceproposals")
public class PriceProposal extends BasicEntity implements Serializable{
	private static final long serialVersionUID = 7882895218501386694L;

	private long member;
	@Column(name="`priceLevel`", nullable=false, length=11)	
	private Integer priceLevel;
	@Column(name="`quantity`", nullable=false, length=11)	
	private Float quantity;
	@Column(name="`price`", nullable=false, length=11)	
	private Float price;
	@Column(name="`proposalType`", nullable=false, length=11)	
	private int proposalType ;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proposal_id", nullable = false)
	private Proposal proposal ;

}
