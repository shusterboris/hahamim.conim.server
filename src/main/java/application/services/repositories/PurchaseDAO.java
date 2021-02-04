package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import application.entities.Purchase;

public interface PurchaseDAO extends CrudRepository<Purchase, Long> {
	public List<Purchase> findAll();

	public Optional<Purchase> findById(Long id);
}
