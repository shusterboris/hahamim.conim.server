package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import application.entities.PriceProposal;
import application.entities.Proposal;

@Repository
public interface PproposalDAO extends CrudRepository<PriceProposal, Long> {
	public List<PriceProposal> findAll();

	public Optional<PriceProposal> findById(Long id);

	public List<PriceProposal> findByMemberAndProposalAndIsDeleted(Long m, Proposal p, boolean isDelete);

	public List<PriceProposal> findByMemberAndProposalAndProposalTypeAndIsDeleted(Long m, Proposal p, int proposalType,
			boolean isDelete);

	public List<PriceProposal> findByProposalAndPriceLevelAndProposalType(Proposal p, int priceLevel, int proposalType);

// сколько собрано заявок по уровню цен
	@Query("SELECT SUM(quantity) FROM PriceProposal p WHERE p.priceLevel=:level AND p.proposal=:proposal AND p.proposalType=:type")
	public float calcTotalByLevel(@Param("proposal") Proposal proposal, @Param("level") int level,
			@Param("type") int type);

	@Modifying
	@Transactional
	@Query("UPDATE PriceProposal p SET p.status=1, p.delivery=:deliveryAddress, p.orderId=:iid WHERE id in (:intentIds)")
	public void createOrdersFromCart(@Param("deliveryAddress") String deliveryAddress,
			@Param("iid") String iid, @Param("intentIds") List<Long> intentIds);
}
