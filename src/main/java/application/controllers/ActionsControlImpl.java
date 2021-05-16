package application.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Utils.LocalDateJsonAdapter;
import application.entities.AppImage;
import application.entities.BusinessPartner;
import application.entities.Delivery;
import application.entities.PageResponse;
import application.services.ActionService;
import application.services.BPservice;
import application.services.ClientService;
import application.services.repositories.PproposalDAO;
import enums.PriceProposalType;
import enums.ProposalStatus;
import net.minidev.json.JSONObject;
import proxies.ActionsSummaryInfo;
import proxies.OrderFromTelegram;
import proxies.OrdersFromTelegram;
import proxies.Payment;
import proxies.PriceProposal;
import proxies.Proposal;
import proxies.Purchase;

@RestController
public class ActionsControlImpl implements ActionsControl {
	// private MockService mService = new MockService();
	@Autowired
	private ActionService actionService;
	@Autowired
	private BPservice bpserv;
	@Autowired
	private ClientService cserv;
	@Autowired
	private PproposalDAO ppDAO;

	private Proposal entityToProposalProxy(application.entities.Proposal p, boolean fullInfo) {
		Proposal pp = new Proposal();
		pp.setId(p.getId());
		pp.setCategory(p.getCategory());
		pp.setRegion(p.getRegion());
		pp.setPrice(p.getPrice());
		pp.setInitiator(p.getInitiator());
		pp.setLastPrice(p.getLastPrice());
		pp.setDueDate(p.getDueDate());
		pp.setMeasure(p.getMeasure());
		pp.setThreshold(p.getThreshold());
		pp.setThresholdmax(p.getThresholdmax());
		if (p.getSupplier() != null) {
			BusinessPartner s = bpserv.findById(p.getSupplier());
			pp.setSupplierId(p.getSupplier());
			if (s != null)
				pp.setSupplier(s.getFullName());
		}
		pp.setPublicationDate(p.getPublicationDate());
		// Set<AppImage>
		Set<AppImage> li = p.getPhotos();
		List<String> pi = new ArrayList<String>();
		if (!li.isEmpty()) {
			for (AppImage i : li) {
				pi.add(i.getImgPath());
			}
		}
		pp.setPhotos(pi);
		pp.setTotal(p.getTotal());
		pp.setTotalQuantity(p.getTotalQuantity());
		pp.setDateOfSailStarting(p.getDateOfSailStarting());
		pp.setCloseDate(p.getCloseDate());
		pp.setName(p.getName());
		pp.setDescription(p.getDescription());
		pp.setBundle(p.getBundle());
		if (fullInfo) {
			Set<application.entities.PriceProposal> lp = p.getPriceProposals();
			if (lp.isEmpty()) {
				pp.setPriceProposals(new ArrayList<PriceProposal>());
			} else {
				ArrayList<PriceProposal> lpp = new ArrayList<PriceProposal>();
				for (application.entities.PriceProposal e : lp) {
					lpp.add(entityToPproposalProxy(e));
				}
				pp.setPriceProposals(lpp);
			}
		}
		pp.setStatus(ProposalStatus.getMessageKeyByNumber(p.getStatus().intValue()));
		pp.setIntOnly((p.getIntOnly() != null) ? p.getIntOnly() : false);
		return pp;
	}

	@Override
	public ResponseEntity<Object> addOrder(String json) {
		try {
			boolean withDelivery = false;
			application.entities.Member m;
			Gson gson = new Gson();
			OrdersFromTelegram order = gson.fromJson(json, OrdersFromTelegram.class);
			long memberid = order.getMember().getId();
			// если member получен из базы то дописываем недостающее, иначе создаем нового
			if (memberid != 0) {
				boolean isChanged = false;
				m = cserv.getMemberById(memberid);
				if (m.getTelegram() == null) {
					m.setTelegram(order.getMember().getTelegram());
					isChanged = true;
				}
				if (m.getDelivery().isEmpty()) {
					m = cserv.addDelivery(order.getMember().getPreferableAddress(), m);
					isChanged = true;
				} else if (m.getDelivery().size() == 1) {
					// добавить адрес если его нет
					String s = order.getMember().getPreferableAddress();
					if (!s.endsWith("не нужна доставка")) {
						withDelivery = true;
						Iterator<Delivery> ls = m.getDelivery().iterator();
						if (!ls.next().getStreetAddress().equalsIgnoreCase(s)) {
							m = cserv.addDelivery(s, m);
							isChanged = true;
						}
					}
				}
				if (isChanged)
					cserv.updateMember(m);
			} else {
				return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
			}
			// записать заказ.
			application.entities.Proposal proposal = new application.entities.Proposal();
			proposal.setId(0);
			for (OrderFromTelegram o : order.getItems()) {
				application.entities.PriceProposal pp = telegramToPproposal(o);
				// загрузить акцию
				if (proposal.getId() != o.getId()) {
					proposal = actionService.findAction(o.getId()).get();
				}
				pp.setProposal(proposal);
				if (memberid != 0)
					pp.setMember(memberid);
				if (withDelivery)
					pp.setDelivery(order.getMember().getPreferableAddress());
				pp.setOrderId(order.getOrderId());
				pp.setSent(true);
				actionService.saveProposal(pp);
			}
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("action not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private application.entities.PriceProposal telegramToPproposal(OrderFromTelegram o) {
		application.entities.PriceProposal pp = new application.entities.PriceProposal();
		pp.setId(o.getId());
		pp.setPrice(o.getPrice());
		pp.setPriceLevel(1);
		pp.setQuantity(o.getQuantity());
		pp.setProposalType(PriceProposalType.MEMBERS.ordinal());
		return pp;
	}

	@Override
	public ResponseEntity<Object> getAction(Long id) {
		try {
			Optional<application.entities.Proposal> e = actionService.findAction(id);
			if (e == null)
				return new ResponseEntity<Object>("", HttpStatus.NOT_FOUND);
			Proposal res = entityToProposalProxy(e.get(), true);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> getActionByMember(Long memberId, int page, Integer pageSize) {
		// для получения своих акций берем те, по которым у меня есть заявленное
		// количество на покупку и соответствующий статус
		try {
			List<Proposal> res = new ArrayList<Proposal>();
			Page<application.entities.Proposal> le = actionService.fetchProposalsByMember(memberId, page, pageSize);
			if (le.isEmpty())
				new ResponseEntity<Object>(res, HttpStatus.OK);
			for (application.entities.Proposal e : le.getContent()) {
				res.add(entityToProposalProxy(e, false));
			}
			@SuppressWarnings({ "rawtypes", "unchecked" })
			PageResponse result = new PageResponse(res, le.getTotalPages(), le.getTotalElements());
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getMemberPriceIntents(Long proposalId, Long memberId) {
		try {
			List<application.entities.PriceProposal> lpp = actionService.findPriceProposals(proposalId, memberId);
			List<PriceProposal> res = new ArrayList<PriceProposal>();
			if (!lpp.isEmpty()) {
				for (application.entities.PriceProposal e : lpp) {
					res.add(entityToPproposalProxy(e));
				}
			}
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getMemberPriceIntentsBoth(Long proposalId, Long memberId, Long initiatorId) {
		try {
			List<application.entities.PriceProposal> lpp = actionService.findPriceProposals(proposalId, memberId,
					initiatorId);
			List<PriceProposal> res = new ArrayList<PriceProposal>();
			if (!lpp.isEmpty()) {
				for (application.entities.PriceProposal e : lpp) {
					res.add(entityToPproposalProxy(e));
				}
			}
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> saveMemberPriceIntents(List<PriceProposal> prices) {
		application.entities.Proposal pr = null;
		application.entities.PriceProposal res = null;
		try {
			for (PriceProposal p : prices) {
				if (pr == null)
					pr = actionService.findAction(p.getProposalId()).get();
				res = proxyToPproposalEntity(p, pr);
				res = actionService.saveProposal(res);
			}
			// pr = actionService.calcSumOrders(pr);
			// actionService.update(pr);
			// сумму теперь считает триггер
			return new ResponseEntity<Object>(res.getId(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> saveAction(String json) {
		try {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter().nullSafe())
					.create();
			Proposal proposal = gson.fromJson(json, Proposal.class);
			application.entities.Proposal pe = updateProxyFromProposalEntity(proposal);
			pe = actionService.save(pe);
			if (pe == null)
				return new ResponseEntity<Object>("action not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			JSONObject ret = new JSONObject();
			ret.put("id", String.valueOf(pe.getId()));
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("action not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<Object> addAction(String json) {
		try {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter().nullSafe())
					.create();
			Proposal proposal = gson.fromJson(json, Proposal.class);
			application.entities.Proposal pe = proxyToProposalEntity(proposal);
			pe = actionService.save(pe);
			if (pe == null)
				return new ResponseEntity<Object>("action not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			JSONObject ret = new JSONObject();
			ret.put("id", String.valueOf(pe.getId()));
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("action not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private application.entities.PriceProposal createPproposal(application.entities.Proposal p, float price,
			Long member, int plevel, int ptype, float q) {
		application.entities.PriceProposal ppe = new application.entities.PriceProposal();
		ppe.setPrice(price);
		ppe.setMember(member);
		ppe.setPriceLevel(plevel);
		ppe.setProposalType(ptype);
		ppe.setQuantity(q);
		ppe.setProposal(p);
		return ppe;
	}

	private application.entities.PriceProposal proxyToPproposalEntity(PriceProposal pp,
			application.entities.Proposal p) {
		application.entities.PriceProposal ppe = new application.entities.PriceProposal();
		ppe.setPrice(pp.getPrice());
		ppe.setMember(pp.getMemberId());
		ppe.setPriceLevel(pp.getPriceLevel());
		ppe.setProposalType(pp.getProposalType());
		ppe.setQuantity(pp.getQuantity());
		ppe.setProposal(p);
		if (pp.getId() != null)
			ppe.setId(pp.getId());
		ppe.setDelivery(pp.getDelivery());
		return ppe;
	}

	private PriceProposal entityToPproposalProxy(application.entities.PriceProposal pe) {
		PriceProposal pp = new PriceProposal();
		pp.setId(pe.getId());
		pp.setPrice(pe.getPrice());
		pp.setMemberId(pe.getMember());
		pp.setPriceLevel(pe.getPriceLevel());
		pp.setProposalType(pe.getProposalType());
		pp.setQuantity(pe.getQuantity());
		pp.setProposalId(pe.getProposal().getId());
		pp.setDelivery(pe.getDelivery());
		return pp;
	}

	private application.entities.Proposal updateProxyFromProposalEntity(Proposal pp) {
		application.entities.Proposal pe = new application.entities.Proposal();
		pe.setCategory(pp.getCategory());
		pe.setRegion(pp.getRegion());
		pe.setPrice(pp.getPrice());
		pe.setInitiator(pp.getInitiator());
		pe.setLastPrice(pp.getLastPrice());
		pe.setDueDate(pp.getDueDate());
		pe.setMeasure(pp.getMeasure());
		pe.setThreshold(pp.getThreshold());
		pe.setThresholdmax(pp.getThresholdmax());
		pe.setStatus(ProposalStatus.getOrdinalByMessage(pp.getStatus()));
		pe.setSupplier(pp.getSupplierId());
		pe.setTotalQuantity(pp.getTotalQuantity());
		pe.setPublicationDate(pp.getPublicationDate());
		List<String> li = pp.getPhotos();
		Set<AppImage> le = new HashSet<AppImage>();
		if (li != null) {
			for (String im : li) {
				le.add(new AppImage(im, pe));
			}
		}
		pe.setPhotos(le);
		pe.setTotal(pp.getTotal());
		pe.setDateOfSailStarting(pp.getDateOfSailStarting());
		pe.setCloseDate(pp.getCloseDate());
		pe.setName(pp.getName());
		pe.setDescription(pp.getDescription());
		pe.setBundle(pp.getBundle());
		List<PriceProposal> lpp = pp.getPriceProposals();
		Set<application.entities.PriceProposal> sppe = new HashSet<application.entities.PriceProposal>();
		pe.setPriceProposals(sppe);
		pe.setId(pp.getId());
		pe.setIntOnly(pp.getIntOnly());
		return pe;

	}

	private application.entities.Proposal proxyToProposalEntity(Proposal pp) {
		application.entities.Proposal pe = new application.entities.Proposal();
		pe.setCategory(pp.getCategory());
		pe.setRegion(pp.getRegion());
		pe.setPrice(pp.getPrice());
		pe.setInitiator(pp.getInitiator());
		pe.setLastPrice(pp.getLastPrice());
		pe.setDueDate(pp.getDueDate());
		pe.setMeasure(pp.getMeasure());
		pe.setThreshold(pp.getThreshold());
		pe.setThresholdmax(pp.getThresholdmax());
		pe.setStatus(ProposalStatus.getOrdinalByMessage(pp.getStatus()));
		pe.setSupplier(pp.getSupplierId());
		pe.setTotalQuantity(pp.getTotalQuantity());
		pe.setPublicationDate(pp.getPublicationDate());
		List<String> li = pp.getPhotos();
		Set<AppImage> le = new HashSet<AppImage>();
		if (li != null) {
			for (String im : li) {
				le.add(new AppImage(im, pe));
			}
		}
		pe.setPhotos(le);
		pe.setTotal(pp.getTotal());
		pe.setDateOfSailStarting(pp.getDateOfSailStarting());
		pe.setCloseDate(pp.getCloseDate());
		pe.setName(pp.getName());
		pe.setDescription(pp.getDescription());
		pe.setBundle(pp.getBundle());
		List<PriceProposal> lpp = pp.getPriceProposals();
		Set<application.entities.PriceProposal> sppe = new HashSet<application.entities.PriceProposal>();
		for (PriceProposal priceP : lpp) {
			sppe.add(proxyToPproposalEntity(priceP, pe));
		}
		pe.setPriceProposals(sppe);
		pe.setId(pp.getId());
		pe.setIntOnly(pp.getIntOnly());
		return pe;
	}

	private Purchase entityToPurchaseProxy(application.entities.Purchase pe) {
		Purchase p = new Purchase();
		p.setId(pe.getId());
		p.setCurrDate(pe.getCurrDate());
		p.setInitiator(pe.getInitiator());
		p.setName(pe.getName());
		p.setState(pe.getState());
		// p.setState(ProposalStatus.getMessageKeyByNumber((int)pe.getState()));
		// setState(ProposalStatus.PUBLISHED);
		p.setSumOrders(sumOrders(pe.getId()));
		return p;

	}

	private application.entities.Purchase proxyToPurchaseEntity(Purchase p) {
		application.entities.Purchase pe = new application.entities.Purchase();
		if (p.getId() != null)
			pe.setId(p.getId());
		pe.setCurrDate(p.getCurrDate());
		pe.setInitiator(p.getInitiator());
		pe.setName(p.getName());
		pe.setState(p.getState());
		return pe;
	}

	// должна возвращать сумму всех заказов
	private Float sumOrders(long id) {
		return actionService.fetchPurchaseTotal(id);
	}

	@Override
	public ResponseEntity<Object> addPurchase(String json) {
		try {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter().nullSafe())
					.create();
			Purchase purchase = gson.fromJson(json, Purchase.class);
			application.entities.Purchase pe = proxyToPurchaseEntity(purchase);
			pe = actionService.addPurchase(pe);
			if (pe == null)
				return new ResponseEntity<Object>("action not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			JSONObject ret = new JSONObject();
			ret.put("id", String.valueOf(pe.getId()));
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("action not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<Object> testPurAdd() {
		try {
			application.entities.Purchase pe = new application.entities.Purchase();
			pe.setInitiator((long) 3);
			pe.setCurrDate(LocalDate.of(2021, 2, 1));
			pe.setState(ProposalStatus.PUBLISHED);
			pe.setName("кролики");
			pe = actionService.addPurchase(pe);
			pe = new application.entities.Purchase();
			pe.setInitiator((long) 3);
			pe.setCurrDate(LocalDate.of(2021, 3, 2));
			pe.setState(ProposalStatus.PUBLISHED);
			pe.setName("морепродукты");
			pe = actionService.addPurchase(pe);
			if (pe == null)
				return new ResponseEntity<Object>("action not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			JSONObject ret = new JSONObject();
			ret.put("id", String.valueOf(pe.getId()));
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("action not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getActionsByBundle(Long proposalId, int page, Integer pageSize) {
		List<Proposal> res = new ArrayList<Proposal>();
		try {
			Page<application.entities.Proposal> l = actionService.findByBundle(proposalId, page, pageSize);

			if (l.isEmpty())
				return new ResponseEntity<Object>(res, HttpStatus.OK);
			for (application.entities.Proposal pe : l.getContent()) {
				res.add(entityToProposalProxy(pe, false));
			}
			PageResponse result = new PageResponse(res, l.getTotalPages(), l.getTotalElements());
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getAllpurchases() {
		List<Purchase> res = new ArrayList<Purchase>();
		try {
			List<application.entities.Purchase> l = actionService.fetchPurcasesAll();
			if (l.isEmpty())
				return new ResponseEntity<Object>(res, HttpStatus.OK);
			for (application.entities.Purchase pe : l) {
				Purchase pr = entityToPurchaseProxy(pe);
				pr.setSumOrders(sumOrders(pe.getId()));
				res.add(pr);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Object>(res, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getPurchaseSuggestions(String name, Long initiator) {
		List<application.entities.Purchase> result = actionService.fetchPurchaseSuggestions(name, initiator);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getPurchaseSuggestionsByInitiator(Long initiator) {
		List<application.entities.Purchase> result = actionService.fetchActivePurchaseSuggestionsByInitiator(initiator);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getAllPurchaseByPage(int pageNo, Integer pageSize) {
		Page<application.entities.Purchase> itemList = actionService.getAllPurchaseByPage(pageNo, pageSize);
		List<proxies.Purchase> proxies = new ArrayList<>();
		for (application.entities.Purchase pur : itemList.getContent()) {
			proxies.Purchase proxy = entityToPurchaseProxy(pur);
			proxies.add(proxy);
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageResponse result = new PageResponse(proxies, itemList.getTotalPages(), itemList.getTotalElements());
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getPaymentsByPurchaseByPage(Long purchaseId, int pageNo, Integer pageSize) {
		Page<application.entities.Payment> itemList = actionService.fetchPurchasePayments(purchaseId, pageNo, pageSize);
		List<proxies.Payment> proxies = new ArrayList<>();
		for (application.entities.Payment pt : itemList.getContent()) {
			proxies.Payment proxy = entityToPaymentProxy(pt);
			proxies.add(proxy);
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageResponse result = new PageResponse(proxies, itemList.getTotalPages(), itemList.getTotalElements());
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> addPayment(String json) {
		try {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter().nullSafe())
					.create();
			Payment p = gson.fromJson(json, Payment.class);
			application.entities.Payment pe = proxyToPaymentEntity(p);
			pe = actionService.savePayment(pe);
			if (pe == null)
				return new ResponseEntity<Object>("action not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			JSONObject ret = new JSONObject();
			ret.put("id", String.valueOf(pe.getId()));
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("action not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private Payment entityToPaymentProxy(application.entities.Payment pt) {
		Payment res = new Payment();
		res.setDate(pt.getDate());
		res.setDestination(pt.getDestination());
		res.setMember(pt.getMember());
		res.setNote(pt.getNote());
		res.setPurchase(pt.getPurchase());
		res.setSum(pt.getSum());
		return res;
	}

	private application.entities.Payment proxyToPaymentEntity(Payment pr) {
		application.entities.Payment pe = new application.entities.Payment();
		pe.setDestination(pr.getDestination());
		pe.setMember(pr.getMember());
		pe.setNote(pr.getNote());
		pe.setPurchase(pr.getPurchase());
		pe.setSum(pr.getSum());
		return pe;
	}

	@Override
	public ResponseEntity<Object> getAllActionsByPage(int page, Integer pageSize) {
		Page<application.entities.Proposal> itemList = actionService.getAllActionsByPage(page, pageSize);
		List<proxies.Proposal> proxies = new ArrayList<>();
		for (application.entities.Proposal pr : itemList.getContent()) {
			proxies.Proposal proxy = entityToProposalProxy(pr, false);
			proxies.add(proxy);
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageResponse result = new PageResponse(proxies, itemList.getTotalPages(), itemList.getTotalElements());
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getActionsByState(int state, int page, Integer pageSize) {
		Page<application.entities.Proposal> itemList = actionService.findByStatus(state, page, pageSize);
		List<proxies.Proposal> proxies = new ArrayList<>();
		for (application.entities.Proposal pr : itemList.getContent()) {
			proxies.Proposal proxy = entityToProposalProxy(pr, false);
			proxies.add(proxy);
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageResponse result = new PageResponse(proxies, itemList.getTotalPages(), itemList.getTotalElements());
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	public ResponseEntity<Object> createReportBuyersBySupplier(Long id) {
		List<Object[]> answer = actionService.getRepo().createSummaryActionsReportBySupplier(id);
		List<ActionsSummaryInfo> result = new ArrayList<>();
		for (Object[] obj : answer) {
			ActionsSummaryInfo ai = ActionsSummaryInfo.getInstanse(obj);
			result.add(ai);
		}
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	public ResponseEntity<Object> createReportBuyersByGoods(Long id) {
		List<Object[]> answer = actionService.getRepo().createSummaryActionsReportByGoods(id);
		List<ActionsSummaryInfo> result = new ArrayList<>();
		for (Object[] obj : answer) {
			ActionsSummaryInfo ai = ActionsSummaryInfo.getInstanse(obj);
			result.add(ai);
		}
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> createReportDelivery(Long supplierId) {
		List<ActionsSummaryInfo> result = actionService.fetchReportDeliveryData(supplierId);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> fetchMembersCart(Long memberId) {
		List<ActionsSummaryInfo> result = actionService.fetchMembersCart(memberId);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> removePriceIntent(Long intentId) {
		Map<String, String> result = new HashMap<String, String>();
		if (actionService.removePriceIntent(intentId)) {
			result.put("result", "Ok");
		} else {
			result.put("result", "Failure");
		}
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> createOrder(Integer addressType, String deliveryAddress,
			SortedMap<Long, List<Long>> data) {
		// Размещает заказ из корзины путем изменения статуса заявки о покупке и
		// записывая адрес доставки. addressType 1 - если домой, 0 - самовывоз
		// в data Map по поставщикам, и для каждого поставщика список заявое на покупку.
		// результат: 1 - выполнено полностью, 0 - выполнено частично (по первому из
		// поставщиков), -1 - ошибка
		Map<String, Integer> result = new HashMap<>();
		try {
			if (data.size() == 1 || addressType == 1) {
				// доставка домой или весь заказ от одного поставщика
				result.put("result", 1);
				List<Long> allGoods = new ArrayList<>();
				for (List<Long> goods : data.values()) {
					allGoods.addAll(goods);
				}
				String id = allGoods.get(0).toString();
				ppDAO.createOrdersFromCart(deliveryAddress, id, allGoods);
			} else {
				//
				result.put("result", 0);
				String id = data.get(data.firstKey()).get(0).toString();
				ppDAO.createOrdersFromCart(deliveryAddress, id, data.get(data.firstKey()));
			}
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.put("result", -1);
			return new ResponseEntity<Object>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
