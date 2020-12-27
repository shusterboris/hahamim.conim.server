package application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import proxies.Member;
import proxies.Person;


@RequestMapping("/clients")
public interface ClientsControl {

	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAll();

	@GetMapping(value = "/staff")
	public ResponseEntity<Object> getStaff();



	@GetMapping(value = "/partner/{id}")
	public ResponseEntity<Object> getPartnerById(@PathVariable(value = "id") Long id);

	@PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> userLogin(@RequestBody Person user);
	
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<Object> getClientById(@PathVariable(value = "id") Long id);

	@PostMapping(value = "/add")
	public ResponseEntity<Object> createClient(@RequestBody String json);
	

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Object> updateClient(@PathVariable("id") Long id, @RequestBody Member p);
	

}