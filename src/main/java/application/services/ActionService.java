package application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.entities.PriceProposal;
import application.entities.Proposal;
import application.entities.Purchase;
import application.services.repositories.ActionsDAO;
import application.services.repositories.PproposalDAO;
import application.services.repositories.PurchaseDAO;

@Service
public class ActionService {
	
	@Autowired
	private ActionsDAO repo;
	@Autowired
	private PproposalDAO repoP;
	@Autowired
	private PurchaseDAO repoPur;
	
	public List<Proposal> findActionsAll(){
		return repo.findAll();
	}

	public Proposal save(Proposal pe) {
	 Proposal res = repo.save(pe);
		return res;
	}
	
	public Optional<Proposal> findAction(Long id) {
		return repo.findById(id);
	}

	public List<PriceProposal> findPriceProposals(Long proposalId, Long memberId) {
		Proposal p=new Proposal();
		p.setId(proposalId);
		return repoP.findByMemberAndProposalAndIsDeleted(memberId, p, false);
		
	}
	
	public PriceProposal saveProposal(PriceProposal pe) {
		 PriceProposal res = repoP.save(pe);
			return res;
	}
	
	public List<Proposal> fetchProposalsByMember(Long member){
		return repo.fetchProposals(member);
	}

	public Purchase savePur(Purchase pe) {
		Purchase res = repoPur.save(pe);
		return res;
	}
	
}
