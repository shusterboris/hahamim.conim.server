package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import application.entities.Proposal;

@Repository
public interface ActionsDAO extends CrudRepository<Proposal, Long>{
	public List<Proposal> findAll();
	public Optional<Proposal> findById(Long id);
}
