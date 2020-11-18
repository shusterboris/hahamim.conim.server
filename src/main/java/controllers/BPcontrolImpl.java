package controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import proxies.BusinessPartner;
import proxies.Proposal;
import services.MockService;


@RestController
public class BPcontrolImpl implements BPcontrol {
	private MockService mService = new MockService();
	
	@Override
	public ResponseEntity<Object> getAllPartners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Object> getPartnerById(Long id) {
		try {
			BusinessPartner res = mService.getBusinessPartnerById(id);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
