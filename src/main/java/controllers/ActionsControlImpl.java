package controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import proxies.Proposal;
import services.MockService;


@RestController
public class ActionsControlImpl implements ActionsControl {
	private MockService mService = new MockService();

	@Override
	public ResponseEntity<Object> getAllProposals() {
		try {
			List<Proposal> res = mService.getProposals();
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getAllActions() {
		try {
			List<Proposal> res = mService.getActions();
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
