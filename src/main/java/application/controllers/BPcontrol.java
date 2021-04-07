package application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/partners")
public interface BPcontrol {

	@GetMapping("/all")
	public ResponseEntity<Object> getAllPartners();

	@PutMapping(value = "/save")
	public ResponseEntity<Object> save(@RequestBody String json);

	@GetMapping(value = "/get/{id}")
	public ResponseEntity<Object> getPartnerById(@PathVariable(value = "id") Long id);

	@GetMapping("/suggestion/{string}")
	public ResponseEntity<Object> getPartnersByNameLike(String string);

	@GetMapping(value = { "/addresses/{id}/{page}/{pageSize}", "/addresses/{id}/{page}/{pageSize}/filter" })
	public ResponseEntity<Object> getStores(@PathVariable(value = "id") Long id, @PathVariable(value = "page") int page,
			@PathVariable(value = "pageSize") Integer pageSize,
			@PathVariable(value = "filter", required = false) String filter);

	@DeleteMapping(value = "/addresses/remove/{id}")
	public ResponseEntity<Object> removeStores(@PathVariable(value = "id") Long id);

	@PutMapping(value = "/addresses/save")
	public ResponseEntity<Object> saveStore(@RequestBody String json);

	@GetMapping("/addAll")
	public ResponseEntity<Object> createAll();
}
