package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import application.entities.CatItem;
@Repository
public interface CatItemDAO extends CrudRepository<CatItem, Long>{
	public List<CatItem> findAll();
	public List<CatItem> findByLanguage( String Language);
	public Optional<CatItem> findById(Long id);
	public List<CatItem> findByItemKeyAndValue(String itemKey,String itemValue);
	public List<CatItem> findByItemKeyAndValueAndLanguage(String itemKey,String itemValue, String Language);
}
