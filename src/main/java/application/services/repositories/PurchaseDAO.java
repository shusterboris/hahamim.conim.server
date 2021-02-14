package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import application.entities.Purchase;
import enums.ProposalStatus;

public interface PurchaseDAO extends CrudRepository<Purchase, Long> {
	public List<Purchase> findAll();

	public List<Purchase> findByInitiator(Long id);

	public Optional<Purchase> findById(Long id);

	public List<Purchase> findByInitiatorAndStateLessThanAndNameContainingIgnoreCase(Long initiator,
			ProposalStatus state, String name);

	public List<Purchase> findByInitiatorAndNameContainingIgnoreCase(Long initiator, String name);

	public List<Purchase> findByInitiatorAndStateLessThan(Long initiator, ProposalStatus state);
}
