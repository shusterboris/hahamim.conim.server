package application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/catalogs")
public interface CatalogsControl {

	@GetMapping({ "/items/{key}/{language}", "/items/{key}" })
	public ResponseEntity<Object> getItemsByKey(@PathVariable("key") String key,
			@PathVariable(name = "language", required = false) String language);

	@GetMapping({ "/items/allcats/{key}/{language}", "/items/allcats/{key}" })
	public ResponseEntity<Object> getAllItemsByKey(@PathVariable("key") String key,
			@PathVariable(name = "language", required = false) String language);
//пока не трогать
	@GetMapping({ "/items/all/{language}", "/items/all" })
	public ResponseEntity<Object> getAllItems(@PathVariable(name = "language", required = false) String language);

	@GetMapping({"/items/child/{parentKey}/{language}", "/items/child/{parentKey}"})
	public ResponseEntity<Object> getItemsByParentKey(@PathVariable("parentKey") String parentKey,
			@PathVariable(name = "language", required = false) String language);

	@GetMapping({ "/itemsstring/{key}/{language}", "/items/{key}" })
	public ResponseEntity<Object> getItemStringByKey(@PathVariable("key") String key,
			@PathVariable(name = "language", required = false) String language);

	@GetMapping({"/itemsstring/child/{parentKey}/{language}", "/itemsstring/child/{parentKey}"})
	public ResponseEntity<Object> getItemStringByParentKey(@PathVariable("parentKey") String parentKey,
			@PathVariable(name = "language", required = false) String language);

	@GetMapping({ "/regions/{language}", "/regions" })
	public ResponseEntity<Object> getRegionsList(@PathVariable(name = "language", required = false) String language);

	@GetMapping({ "/regionsstrings/{language}", "/regionsstrings" })
	public ResponseEntity<Object> getRegionsStrings(@PathVariable(name = "language", required = false) String language);

	@GetMapping({ "/settlments/{region}/{language}", "/settlments/{region}" })
	public ResponseEntity<Object> getSetllmetsList(@PathVariable("region") String region,
			@PathVariable(name = "language", required = false) String language);

	@GetMapping({ "/settlmentsstrings/{region}/{language}", "/settlmentsstrings/{region}" })
	public ResponseEntity<Object> getSetllmetsStrings(@PathVariable("region") String region,
			@PathVariable(name = "language", required = false) String language);
	
	@GetMapping(value = "/addAll")
	public ResponseEntity<Object> createAll();

}
