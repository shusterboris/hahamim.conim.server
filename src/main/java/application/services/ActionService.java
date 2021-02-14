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
import enums.ProposalStatus;

@Service
public class ActionService {

	@Autowired
	private ActionsDAO repo;
	@Autowired
	private PproposalDAO repoP;
	@Autowired
	private PurchaseDAO repoPur;

	public List<Proposal> findActionsAll() {
		return repo.findAll();
	}

//для новых
	public Proposal save(Proposal pe) {
		Proposal res = repo.save(pe);
		return res;
	}

	public Proposal update(Proposal pe) {
		Optional<Proposal> res = repo.findById(pe.getId());
		if (res.isPresent()) {
			Proposal np = res.get();
			pe.setCreated(np.getCreated());
			pe.setModified(np.getModified());
			pe.setVersion(np.getVersion());
		}
		pe = repo.save(pe);
		return pe;
	}

	public Optional<Proposal> findAction(Long id) {
		return repo.findById(id);
	}

	public List<PriceProposal> findPriceProposals(Long proposalId, Long memberId) {
		Proposal p = new Proposal();
		p.setId(proposalId);
		return repoP.findByMemberAndProposalAndIsDeleted(memberId, p, false);

	}

	public PriceProposal saveProposal(PriceProposal pe) {

		PriceProposal res = repoP.save(pe);
		return res;
	}

	public List<Proposal> fetchProposalsByMember(Long member) {
		return repo.fetchProposals(member);
	}

	public Purchase savePur(Purchase pe) {
		Purchase res = repoPur.save(pe);
		return res;
	}

	public List<Proposal> findByBundle(Long id) {
		return repo.findByBundle(id);
	}

	public float fetchPurchaseTotal(Long id) {
		return repo.fetchPurchaseTotal(id);
	}

	public List<Purchase> fetchPurcasesAll() {
		return repoPur.findAll();
	}

	public Proposal calcSumOrders(Proposal p) {
		// id level type
		float sum = 0;
		float boundary = 0;
		List<PriceProposal> l;
		for (int i = 3; i > 0; i--) {
			sum = repoP.calcTotalByLevel(p.getId(), i, 1);
			l = repoP.findByProposalAndPriceLevelAndProposalType(p, i, 0);
			if (!l.isEmpty())
				boundary = l.get(0).getQuantity();
			if (sum >= boundary) {
				p.setLastPrice(l.get(0).getPrice());
				p.setTotal(sum);
				return p;
			}
		}
		return p;
	}

	public List<Purchase> fetchPurchaseSuggestions(String name, Long initiator) {
		return repoPur.findByInitiatorAndStateLessThanAndNameContainingIgnoreCase(initiator, ProposalStatus.ARCHIVE,
				name);
	}

	public List<Purchase> fetchActivePurchaseSuggestionsByInitiator(Long initiator) {
		return repoPur.findByInitiatorAndStateLessThan(initiator, ProposalStatus.ARCHIVE);
	}

}
