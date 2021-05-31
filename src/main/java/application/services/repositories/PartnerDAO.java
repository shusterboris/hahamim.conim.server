package application.services.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import application.entities.BusinessPartner;

@Repository
public interface PartnerDAO extends PagingAndSortingRepository<BusinessPartner, Long> {
	public List<BusinessPartner> findAll();

	public Optional<BusinessPartner> findById(Long id);

	public List<BusinessPartner> findByFullNameContaining(String fullName);

	@Query(nativeQuery = true, value = "SELECT * FROM partn_memb  WHERE partn_id =:bp and memb_id =:m")
	public List<Object> findMember(@Param("bp") Long bp, @Param("m") Long m);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "INSERT INTO partn_memb (partn_id, memb_id)	VALUES (:bp, :m)")
	public void saveMemberPartnerRelation(@Param("bp") Long bp, @Param("m") Long m);

}
