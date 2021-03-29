package application.services.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import application.entities.Delivery;

public interface DeliveryDAO extends PagingAndSortingRepository<Delivery, Long> {
	Page<Delivery> findByMember_id(Long id, Pageable p);

	Page<Delivery> findByMember_idAndStreetAddressContaining(Long id, Pageable p, String filter);
}
