package application.services.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import application.entities.BusinessPartner;


@Repository
public interface PartnerDAO  extends CrudRepository<BusinessPartner, Long>{

}
