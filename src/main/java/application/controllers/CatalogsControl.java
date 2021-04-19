package application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/catalogs")
public interface CatalogsControl {

	@GetMapping({ "/items/all/{language}", "/items/all" })
	public ResponseEntity<Object> getAllItems(@PathVariable(name = "language", required = false) String language);

	@GetMapping({ "/items/page/{page}/{language}", "/items/page/{page}" })
	public ResponseEntity<Object> getAllItemsPage(@PathVariable(name = "page") int page,
			@PathVariable(name = "language", required = false) String language);

	@GetMapping({ "/regionsstrings/{language}", "/regionsstrings" })
	public ResponseEntity<Object> getRegionsStringList(
			@PathVariable(name = "language", required = false) String language);

	@GetMapping(value = "/addAll")
	public ResponseEntity<Object> createAll();

}
