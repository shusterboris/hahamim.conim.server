package application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import application.entities.Payment;
import application.entities.PriceProposal;
import application.entities.Proposal;
import application.entities.Purchase;
import application.services.repositories.ActionsDAO;
import application.services.repositories.PaymentDAO;
import application.services.repositories.PproposalDAO;
import application.services.repositories.PurchaseDAO;
import enums.PriceProposalType;
import enums.ProposalStatus;
import proxies.ActionsSummaryInfo;

@Service
public class ActionService {

	@Autowired
	private ActionsDAO repo;
	@Autowired
	private PproposalDAO repoP;
	@Autowired
	private PurchaseDAO repoPur;
	@Autowired
	private PaymentDAO repoPay;

	// для новых
	public Proposal save(Proposal pe) {
		if (pe.getTotal() == 0)
			pe.setTotal((float) 0);
		if (pe.getTotalQuantity() == null)
			pe.setTotalQuantity((float) 0);
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

	public PriceProposal updateIntentState(PriceProposal pe) {
		Optional<PriceProposal> res = repoP.findById(pe.getId());
		if (res.isPresent()) {
			PriceProposal np = res.get();
			np.setStatus(pe.getStatus());
			np = repoP.save(np);
			return np;
		}
		return null;
	}

	public PriceProposal updateP(PriceProposal pe) {
		Optional<PriceProposal> res = repoP.findById(pe.getId());
		if (res.isPresent()) {
			PriceProposal np = res.get();
			pe.setCreated(np.getCreated());
			pe.setModified(np.getModified());
			pe.setVersion(np.getVersion());
			Float amount = (float) 0.0;
			if (pe.getProposalType() == 1 && pe.getPrice() != null && pe.getQuantity() != null) {
				amount = pe.getPrice() * pe.getQuantity();
			}
			amount = Utils.RoundNumber.round(amount, 2);
			pe.setAmount(amount);
		}
		pe = repoP.save(pe);
		return pe;
	}

	public Optional<Proposal> findAction(Long id) {
		return repo.findById(id);
	}

	public List<PriceProposal> findPriceProposals(Long proposalId, Long memberId) {
		Proposal p = new Proposal();
		p.setId(proposalId);
		List<PriceProposal> result = repoP.findByMemberAndProposalAndProposalTypeAndIsDeleted(memberId, p,
				PriceProposalType.MEMBERS.ordinal(), false);
		return result;
	}

	public List<PriceProposal> findPriceProposals(Long proposalId, Long memberId, Long initiatorId) {
		Proposal p = new Proposal();
		p.setId(proposalId);
		List<PriceProposal> result = repoP.findByMemberAndProposalAndProposalTypeAndIsDeleted(initiatorId, p,
				PriceProposalType.PARTNERS.ordinal(), false);
		List<PriceProposal> intents = repoP.findByMemberAndProposalAndProposalTypeAndIsDeleted(memberId, p,
				PriceProposalType.MEMBERS.ordinal(), false);
		if (intents != null && intents.size() > 0)
			result.addAll(intents);
		return result;
	}

	public PriceProposal saveProposal(PriceProposal pe) {
		PriceProposal res = updateP(pe);
		return res;
	}

	public Page<Proposal> fetchProposalsByMember(Long member, int pageNo, Integer pageSize) {
		if (pageNo < 0)
			pageNo = 0;
		PageRequest request = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
		return repo.fetchProposals(member, request);
	}

	public Purchase addPurchase(Purchase pe) {
		Purchase res;
		Long initiatorId = pe.getInitiator();
		String purchaseName = pe.getName();
		List<Purchase> found = repoPur.findByInitiatorAndStateLessThanAndNameContainingIgnoreCase(initiatorId,
				ProposalStatus.ARCHIVE, purchaseName);
		if (found == null || found.size() == 0)
			res = repoPur.save(pe);
		else if (found.stream().noneMatch(entity -> entity.getName().equals(purchaseName)))
			res = repoPur.save(pe);
		else {
			res = new Purchase();
			res.setName("error: purchase exists");
			res.setId((long) 0);
		}
		return res;
	}

	public Page<Proposal> findByBundle(Long id, int pageNo, Integer pageSize) {
		if (pageNo < 0)
			pageNo = 0;
		PageRequest request = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
		return repo.findByBundle(id, request);
	}

	public float fetchPurchaseTotal(Long id) {
		Float sum = repo.fetchPurchaseTotal(id);
		return sum != null ? sum.floatValue() : (float) 0;
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
			sum = repoP.calcTotalByLevel(p, i, 1);
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

	public Page<Purchase> getAllPurchaseByPage(int pageNo, Integer pageSize) {
		if (pageNo < 0)
			pageNo = 0;
		PageRequest request = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
		return repoPur.findAll(request);
	}

	public float fetchPurchaseSumPay(Long id) {
		Float sum = repoPay.fetchPaymentByPurchase(id);
		return sum != null ? sum.floatValue() : (float) 0;
	}

	public Page<Payment> fetchPurchasePayments(Long id, int pageNo, Integer pageSize) {
		if (pageNo < 0)
			pageNo = 0;
		PageRequest p = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
		return repoPay.findByPurchase(id, p);
	}

	// TODO доделать новый платеж
	public Payment savePayment(Payment pe) {
		Payment res = repoPay.save(pe);
		return res;
	}

	public Page<Proposal> getAllActionsByPage(int pageNo, Integer pageSize) {
		if (pageNo < 0)
			pageNo = 0;
		PageRequest request = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
		return repo.findAll(request);
	}

	public Page<Proposal> findByStatus(int status, int pageNo, Integer pageSize) {
		if (pageNo < 0)
			pageNo = 0;
		PageRequest p = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
		return repo.findByStatus(status, p);
	}

	public List<ActionsSummaryInfo> fetchReportDeliveryData(Long supplierId) {
		List<Object[]> answer = getRepo().createSummaryDeliveryReport(supplierId);
		List<ActionsSummaryInfo> result = new ArrayList<>();
		for (Object[] obj : answer) {
			ActionsSummaryInfo ai = ActionsSummaryInfo.getInstanse(obj);
			result.add(ai);
		}
		return result;
	}

	public List<ActionsSummaryInfo> fetchMembersCart(Long memberId) {
		List<Object[]> answer = getRepo().fetchMembersCart(memberId);
		List<ActionsSummaryInfo> result = new ArrayList<>();
		for (Object[] obj : answer) {
			ActionsSummaryInfo ai = ActionsSummaryInfo.getInstanse(obj);
			result.add(ai);
		}
		return result;
	}

	public boolean removePriceIntent(Long intentId) {
		try {
			repoP.deleteById(intentId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<ActionsSummaryInfo> fetchOrdersByPartner(Long partnerId, Integer status) {
		List<Object[]> answer = repo.fetchOrdersByPartner(partnerId, status);
		List<ActionsSummaryInfo> result = new ArrayList<>();
		for (Object[] obj : answer) {
			ActionsSummaryInfo ai = ActionsSummaryInfo.getInstanse(obj);
			result.add(ai);
		}
		return result;
	}

	public List<ActionsSummaryInfo> fetchOrdersByCustomer(Long memberId, Integer status) {
		List<Object[]> answer = repo.fetchOrdersByCustomer(memberId, status);
		List<ActionsSummaryInfo> result = new ArrayList<>();
		for (Object[] obj : answer) {
			ActionsSummaryInfo ai = ActionsSummaryInfo.getInstanse(obj);
			result.add(ai);
		}
		return result;
	}

	public List<ActionsSummaryInfo> fetchOrdersByOrderNo(String orderId) {
		List<Object[]> answer = repo.fetchOrdersByOrderNo(orderId);
		List<ActionsSummaryInfo> result = new ArrayList<>();
		for (Object[] obj : answer) {
			ActionsSummaryInfo ai = ActionsSummaryInfo.getInstanse(obj);
			result.add(ai);
		}
		return result;
	}

	public String changeActionStatus(Long id, int status) {
		try {
			Optional<Proposal> propRec = findAction(id);
			if (!propRec.isPresent())
				throw new Exception("Proposal with id " + Long.toString(id) + " does not exists");
			Proposal p = propRec.get();
			p.setStatus(status);
			repo.save(p);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public ActionsDAO getRepo() {
		return repo;
	}

	public void setRepo(ActionsDAO repo) {
		this.repo = repo;
	}
}
