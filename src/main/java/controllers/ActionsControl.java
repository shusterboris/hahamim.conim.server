package controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public interface ActionsControl {

	@GetMapping("/proposals/all")
	public ResponseEntity<Object> getAllProposals();
	
	@GetMapping("/actions/all")
	public ResponseEntity<Object> getAllActions();
	
	@GetMapping("/actions/get/{id}")
	public ResponseEntity<Object> getAction(@PathVariable("id") Long id);
	
	@PutMapping("/actions/update")
	public ResponseEntity<Object> saveAction();

	@PutMapping("/actions/add")
	public ResponseEntity<Object> addAction();
	
}
