package controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/actions")
public interface ActionsControl {

	@GetMapping("/proposals/all")
	public ResponseEntity<Object> getAllProposals();
	@GetMapping("/actions/all")
	public ResponseEntity<Object> getAllActions();
}
