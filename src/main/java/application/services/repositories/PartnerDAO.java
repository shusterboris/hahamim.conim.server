package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import application.entities.BusinessPartner;

@Repository
public interface PartnerDAO extends PagingAndSortingRepository<BusinessPartner, Long> {
	public List<BusinessPartner> findAll();

	public Optional<BusinessPartner> findById(Long id);

	public List<BusinessPartner> findByFullNameContaining(String fullName);

}
