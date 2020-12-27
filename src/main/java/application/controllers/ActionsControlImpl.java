package application.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Utils.LocalDateJsonAdapter;
import application.services.ActionService;
import application.services.MockService;
import net.minidev.json.JSONObject;
import proxies.PriceProposal;
import proxies.Proposal;




@RestController
public class ActionsControlImpl implements ActionsControl {
	private MockService mService = new MockService();
	@Autowired
	private ActionService actionService;

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

	@Override
	public ResponseEntity<Object> getAction(Long id) {
		try {
			Proposal res = mService.getAction(id);
			if (res != null)
				return new ResponseEntity<Object>(res, HttpStatus.OK);
			else
				return new ResponseEntity<Object>("Record not found: ".concat(String.valueOf(id)), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<Object> getActionByMember(Long memberId){
		//для получения своих акций берем те, по которым у меня есть заявленное количество
		//на покупку и соответствующий статус
		try {
			List<Proposal> res = mService.getAllMemberActions(memberId);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> addAction(String json) {
		try {
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter().nullSafe())
					.create();
			Proposal proposal = gson.fromJson(json, Proposal.class);
			Long id = mService.addAction(proposal);
			proposal.setId(id);
			JSONObject ret = new JSONObject();
			ret.put("id", String.valueOf(id));
			return new ResponseEntity<>(ret, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Object>("action not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@Override
	public ResponseEntity<Object> saveAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Object> getMemberPriceIntents(Long proposalId, Long memberId) {
		try {
			List<PriceProposal> res = mService.getMembersPriceIntents(proposalId, memberId);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> saveMemberPriceIntents(List<PriceProposal> prices) {
		try {
			String res = mService.saveMemberPriceIntents(prices);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

	@Override
	public ResponseEntity<Object> addNewAction(String json) {
		actionService.findActionsAll();
		return new ResponseEntity<>(HttpStatus.OK);
	}


}
