package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import application.entities.Member;
import application.entities.PriceProposal;
import application.entities.Proposal;

@Repository
public interface PproposalDAO extends CrudRepository<PriceProposal, Long> {
	public List<PriceProposal> findAll();
	public Optional<PriceProposal> findById(Long id);
	public List<PriceProposal> findByMemberAndProposal(Long m, Proposal p);
}