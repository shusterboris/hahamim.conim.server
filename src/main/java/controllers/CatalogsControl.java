package controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import entities.Item;

@RequestMapping("/catalogs")
public interface CatalogsControl {

	@GetMapping("/items/{key}")
	public ResponseEntity<Item> getItemsByKey(@PathVariable("key") String key);

	@GetMapping("/items/{parentKey}")
	public ResponseEntity<Item> getItemsByParentKey(@PathVariable("parentKey") String parentKey);

	@GetMapping("/regions/{language}")
	public ResponseEntity<Item> getRegionsList(@PathVariable("language") String language);

	@GetMapping("/regionsstrings")
	public ResponseEntity<Item> getRegionsStrings(@PathVariable("language") String language);

	@GetMapping("/settlments/{region}{language}")
	public ResponseEntity<Item> getSetllmetsList(@PathVariable("region") String region,
			@PathVariable("language") String language);

	@GetMapping("/settlmentsstrings/{region}{language}")
	public ResponseEntity<Item> getSetllmetsStrings(@PathVariable("region") String region,
			@PathVariable("language") String language);

}
