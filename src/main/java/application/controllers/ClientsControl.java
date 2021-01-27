package application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/clients")
public interface ClientsControl {

	@GetMapping(value = "/all")
	public ResponseEntity<Object> getAll();

	@GetMapping(value = "/login/{user}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> userLogin(@PathVariable(value = "user") String user);
	
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<Object> getClientById(@PathVariable(value = "id") Long id);

	@PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> createClient(@RequestBody String json);
	

	//@PutMapping(value = "/update/{id}")
	//public ResponseEntity<Object> updateClient(@PathVariable("id") Long id, @RequestBody Member p);
	@PutMapping(value = "/update")
	public ResponseEntity<Object> updateClient(@RequestBody String json);	
	
	@GetMapping(value = "/addAll")
	public ResponseEntity<Object> createAll();
}
