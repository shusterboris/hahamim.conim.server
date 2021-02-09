package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import application.entities.PriceProposal;
import application.entities.Proposal;

@Repository
public interface PproposalDAO extends CrudRepository<PriceProposal, Long> {
	public List<PriceProposal> findAll();
	public Optional<PriceProposal> findById(Long id);
	public List<PriceProposal> findByMemberAndProposalAndIsDeleted(Long m, Proposal p, boolean isDelete);

	public List<PriceProposal> findByProposalAndPriceLevelAndProposalType(Proposal p, int priceLevel, int proposalType);
// сколько собрано заявок по уровню цен
	@Query("SELECT SUM(quantity) FROM PriceProposal p WHERE p.priceLevel=:level AND p.proposal=:id AND p.proposalType=:type")
	public float calcTotalByLevel(@Param("id") Long id, @Param("level") int level, @Param("type") int type);
}
