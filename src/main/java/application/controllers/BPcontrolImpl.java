package application.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import application.services.BPservice;
import application.services.MockService;
import proxies.Address;
import application.entities.BusinessPartner;



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
    private proxies.BusinessPartner convertPartnerToProxy(BusinessPartner pe){
    	///Long id, String name, Address address, Long headQuatersId/
		
		  proxies.BusinessPartner p=new proxies.BusinessPartner(pe.getId(),pe.getFullName(),null,(long) 0);
		  p.setFullName(pe.getFullName()); return p;
		 
    
    }
}
