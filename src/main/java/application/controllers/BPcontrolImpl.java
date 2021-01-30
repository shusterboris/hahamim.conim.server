package application.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import application.entities.BusinessPartner;
import application.entities.Store;
import application.services.BPservice;



@RestController
public class BPcontrolImpl implements BPcontrol {
	//private MockService mService = new MockService();
	@Autowired
	private BPservice serv;
	@Override
	public ResponseEntity<Object> getAllPartners() {
		List<BusinessPartner> all = serv.findAll();
		if (all==null)return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);;
		ArrayList<proxies.BusinessPartner> pall = new ArrayList<proxies.BusinessPartner>();
		for (BusinessPartner p:all) {
			pall.add(convertPartnerToProxy(p));
		}
		return new ResponseEntity<Object>(pall, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getPartnerById(Long id) {
		try {
			//BusinessPartner res = mService.getBusinessPartnerById(id);
			BusinessPartner pe = serv.findbyId(id);
			if (pe==null) return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
			proxies.BusinessPartner res=convertPartnerToProxy(pe);
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

			}
		}
		return p;

    }
}
