package application.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import application.entities.BusinessPartner;
import application.entities.Store;
import application.services.BPservice;
import application.services.CatItemServices;
import application.services.repositories.StoresDAO;

@RestController
public class BPcontrolImpl implements BPcontrol {
	// private MockService mService = new MockService();
	@Autowired
	private BPservice serv;
	@Autowired
	private StoresDAO storesDAO;
	@Autowired
	private CatItemServices catServ;

	@Override
	public ResponseEntity<Object> getAllPartners() {
		List<BusinessPartner> all = serv.findAll();
		if (all == null)
			return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
		;
		ArrayList<proxies.BusinessPartner> pall = new ArrayList<proxies.BusinessPartner>();
		for (BusinessPartner p : all) {
			pall.add(convertPartnerToProxy(p));
		}
		return new ResponseEntity<Object>(pall, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getPartnerById(Long id) {
		try {
			// BusinessPartner res = mService.getBusinessPartnerById(id);
			BusinessPartner pe = serv.findbyId(id);
			if (pe == null)
				return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
			proxies.BusinessPartner res = convertPartnerToProxy(pe);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> createAll() {
		serv.createAll();
		return new ResponseEntity<Object>("", HttpStatus.OK);
	}

	private proxies.BusinessPartner convertPartnerToProxy(BusinessPartner pe) {
		proxies.BusinessPartner p = new proxies.BusinessPartner();
		p.setId(pe.getId());
		p.setFullName(pe.getFullName());
		p.setSupplier(pe.isSupplier());
		Set<Store> ls = pe.getStores();
		List<proxies.Store> lps = new ArrayList<proxies.Store>();
		if (!ls.isEmpty()) {
			for (Store s : ls) {
				lps.add(convertStoreToProxy(s));
			}
		}
		p.setStores(lps);
		return p;

	}

	private proxies.Store convertStoreToProxy(Store s) {
		proxies.Store sp = new proxies.Store(s.getId(), s.getName(), null);
		sp.setAltitude(s.getAltitude());
		sp.setLatitude(s.getLatitude());
		sp.setStreetAddress(s.getStreetAddress());
		ArrayList<String> l = catServ.getValueById(s.getSettlement());
		sp.setSettlement(l.get(1));
		sp.setRegion(l.get(0));
		return sp;
	}

	@Override
	public ResponseEntity<Object> getPartnersByNameLike(String string) {
		try {
			List<BusinessPartner> result = serv.findByFullNameLike(string);
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<Object> getStores(Long id, int page, Integer pageSize, String filter) {
		if (page < 0)
			page = 0;
		PageRequest request = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "settlement"));

		List<proxies.Store> lstAddr = new ArrayList<proxies.Store>();
		if (filter == null)
			filter = "";
		Page<Store> queryResult = null;
		if ("".equals(filter))
			queryResult = storesDAO.findByBp_id(id, request);
		else
			queryResult = storesDAO.findByBp_idAndStreetAddressContaining(id, request, filter);
		if (queryResult.getSize() > 0) {
			for (Store d : queryResult.getContent()) {
				proxies.Store proxy = convertStoreToProxy(d);
				lstAddr.add(proxy);
			}
		}
		return new ResponseEntity<>(lstAddr, HttpStatus.OK);
	}

}
