package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import application.entities.CatItem;
@Repository
public interface CatItemDAO extends PagingAndSortingRepository<CatItem, Long>{
	public Page<CatItem> findAll(Pageable p);
	public List<CatItem> findAll();
	public List<CatItem> findByLanguage( String Language);
	public Optional<CatItem> findById(Long id);
	public List<CatItem> findByItemKeyAndValue(String itemKey,String itemValue);
	public List<CatItem> findByItemKeyAndValueAndLanguage(String itemKey,String itemValue, String Language);
}
