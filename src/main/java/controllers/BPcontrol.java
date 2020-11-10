package controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/partners")
public interface BPcontrol {
	@GetMapping("/all")
	public ResponseEntity<Object> getAllPartners();
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<Object> getPartnerById(@PathVariable(value = "id") Long id);
}
