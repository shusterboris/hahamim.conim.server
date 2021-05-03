package application.services.repositories;

import java.util.List;
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

	@Query("SELECT p FROM Proposal p, PriceProposal pp WHERE pp.member=:id AND  pp.priceLevel=1 AND pp.proposalType=1   AND p=pp.proposal ")
	public Page<Proposal> fetchProposals(@Param("id") Long id, Pageable p);

	@Query("SELECT SUM(total*last_price) FROM Proposal p WHERE p.bundle=:id")
	public Float fetchPurchaseTotal(@Param("id") Long id);

	public Page<Proposal> findAll();

	@Query("SELECT prop.id FROM PriceProposal prop JOIN Proposal act ON prop.proposal = act.id and act.status = 3  WHERE prop.proposalType = 1 and prop.priceLevel = 1 and act.supplier = ?1")
	public List<Object[]> createOrderReport(@Param("supplier") Long supplier);

	public Page<Proposal> findByStatus(@Param("status") int status, Pageable p);

	@Query(nativeQuery = true, value = "SELECT * FROM actions_report WHERE supplier_id = :supplierId")
	public List<Object[]> createSummaryActionsReportBySupplier(@Param("supplierId") Long supplierId);

	@Query(nativeQuery = true, value = "SELECT * FROM actions_report WHERE proposal_id = :proposalId")
	public List<Object[]> createSummaryActionsReportByGoods(@Param("proposalId") Long proposalId);

	@Query(nativeQuery = true, value = "SELECT *,sum(quantity),sum(quantity * price) FROM actions_report WHERE supplier_id = :supplierId GROUP BY region, settlement, name")
	public List<Object[]> createSummaryDeliveryReport(@Param("supplierId") Long supplierId);

	@Query(nativeQuery = true, value = "SELECT * FROM actions_report WHERE status = 0 AND member = :memberId")
	public List<Object[]> fetchMembersCart(@Param("memberId") Long memberId);

}
