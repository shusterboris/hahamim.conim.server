package application.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import proxies.PriceProposal;

@RequestMapping("/")
public interface ActionsControl {

	@GetMapping("/proposals/all")
	public ResponseEntity<Object> getAllProposals();
	
	@GetMapping("/actions/all")
	public ResponseEntity<Object> getAllActions();
	
	@GetMapping("/actions/get/{id}")
	public ResponseEntity<Object> getAction(@PathVariable("id") Long id);
	
	@GetMapping("/actions/get/member/{memberId}")
	public ResponseEntity<Object> getActionByMember(@PathVariable("memberId") Long memberId);
	
	@PostMapping("/action/add")
	public ResponseEntity<Object> addAction(@RequestBody String json);

	@GetMapping("/actions/intents/get/{proposalId}/{memberId}")
	public ResponseEntity<Object> getMemberPriceIntents(@PathVariable("proposalId") Long proposalId, 
			@PathVariable("memberId") Long  memberId);
	
	@PutMapping(value = "/actions/intents/put", consumes = "application/json", produces = "application/json" )
	public ResponseEntity<Object> saveMemberPriceIntents(@RequestBody List<PriceProposal> price);
	
	@GetMapping("/actions/testAdd")
	public ResponseEntity<Object> testAdd();
}
