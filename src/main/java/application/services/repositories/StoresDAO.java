package application.services.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import application.entities.Store;

public interface StoresDAO extends PagingAndSortingRepository<Store, Long> {
	public Page<Store> findByBp_id(Long bpId, Pageable p);

	public Page<Store> findByBp_idAndStreetAddressContaining(Long id, Pageable p, String filter);

}
