package application.services.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import application.entities.Proposal;

@Repository
public interface ActionsDAO extends PagingAndSortingRepository<Proposal, Long> {


	public Optional<Proposal> findById(Long id);

	public Page<Proposal> findByBundle(Long bundle, Pageable p);

	@Query("SELECT p FROM Proposal p, PriceProposal pp WHERE pp.member=:id AND  pp.priceLevel=1 AND p=pp.proposal ")
	// @Query("SELECT p FROM Proposal p WHERE p.initiator=:id")
	public Page<Proposal> fetchProposals(@Param("id") Long id, Pageable p);

	@Query("SELECT SUM(total*last_price) FROM Proposal p WHERE p.bundle=:id")
	public Float fetchPurchaseTotal(@Param("id") Long id);

	public Page<Proposal> findAll();

}
