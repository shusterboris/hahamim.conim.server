package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import application.entities.BusinessPartner;
import application.entities.Member;


@Repository
public interface PartnerDAO  extends CrudRepository<BusinessPartner, Long>{
	public List<BusinessPartner> findAll();
	public Optional<BusinessPartner> findById(Long id);
}
