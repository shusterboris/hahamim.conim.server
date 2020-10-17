package controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import entities.Person;

@RequestMapping("/")
public interface ClientsControl {

	@GetMapping(value = "/clients")
	public ResponseEntity<Object> getAll();

	@GetMapping(value = "/clients/get/{id}")
	public ResponseEntity<Object> getClientById(@PathVariable(value = "id") Long id);

	@PostMapping(value = "/clients/add", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> createClient(@RequestBody Person p);

	@PutMapping(value = "/clients/update/{id}")
	public ResponseEntity<Object> updateClient(@PathVariable("id") Long id, @RequestBody Person p);

}
