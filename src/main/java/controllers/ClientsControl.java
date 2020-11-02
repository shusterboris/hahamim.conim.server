package controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import proxies.Member;
import proxies.User;

@RequestMapping("/clients")
public interface ClientsControl {

	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAll();

	@GetMapping(value = "/staff")
	public ResponseEntity<Object> getStaff();

	@GetMapping(value = "/members")
	public ResponseEntity<Object> getMembers();

	@PostMapping(value = "/login")
	public ResponseEntity<Object> userLogin(@RequestBody User user);
	
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<Object> getClientById(@PathVariable(value = "id") Long id);

	@PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> createClient(@RequestBody Member p);

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Object> updateClient(@PathVariable("id") Long id, @RequestBody Member p);

}
