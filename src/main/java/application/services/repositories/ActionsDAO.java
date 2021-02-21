package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import application.entities.Proposal;

@Repository
public interface ActionsDAO extends CrudRepository<Proposal, Long> {
	public List<Proposal> findAll();

	public Optional<Proposal> findById(Long id);

	public List<Proposal> findByBundle(Long bundle);

	@Query("SELECT p FROM Proposal p, PriceProposal pp WHERE pp.member=:id AND  pp.priceLevel=1 AND p=pp.proposal ")
	// @Query("SELECT p FROM Proposal p WHERE p.initiator=:id")
	public List<Proposal> fetchProposals(@Param("id") Long id);

	@Query("SELECT SUM(total*last_price) FROM Proposal p WHERE p.bundle=:id")
	public Float fetchPurchaseTotal(@Param("id") Long id);

}
