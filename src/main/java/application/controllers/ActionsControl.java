package application.controllers;

import java.util.List;
import java.util.SortedMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import proxies.PriceProposal;

@RequestMapping("/")
public interface ActionsControl {

	@GetMapping("/purchase/all")
	public ResponseEntity<Object> getAllpurchases();

	@GetMapping("/actions/get/{id}")
	public ResponseEntity<Object> getAction(@PathVariable("id") Long id);

	// для мембера
	@GetMapping("/actions/get/member/{memberId}/{page}/{pageSize}")
	public ResponseEntity<Object> getActionByMember(@PathVariable("memberId") Long memberId,
			@PathVariable(name = "page") int page, @PathVariable(name = "pageSize", required = false) Integer pageSize);

	@PostMapping("/action/add")
	public ResponseEntity<Object> addAction(@RequestBody String json);

	@PostMapping("/action/save")
	public ResponseEntity<Object> saveAction(@RequestBody String json);

	// из телеграма
	@PostMapping(value = "/actions/addOrder", consumes = "application/json;charset=UTF-8")
	public ResponseEntity<Object> addOrder(@RequestBody String json);

	@GetMapping("/actions/intents/get/{proposalId}/{memberId}")
	public ResponseEntity<Object> getMemberPriceIntents(@PathVariable("proposalId") Long proposalId,
			@PathVariable("memberId") Long memberId);

	@GetMapping("/actions/intents/get/both/{proposalId}/{memberId}/{initiatorId}")
	public ResponseEntity<Object> getMemberPriceIntentsBoth(@PathVariable("proposalId") Long proposalId,
			@PathVariable("memberId") Long memberId, @PathVariable("initiatorId") Long initiatorId);

	@PutMapping(value = "/actions/intents/put", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> saveMemberPriceIntents(@RequestBody List<PriceProposal> price);

	@PutMapping(value = "/actions/intents/changeStatus", consumes = "application/json")
	public ResponseEntity<Object> changeStatusIntents(@RequestBody String json);

	@DeleteMapping("/actions/intents/delete/{intentId}")
	public ResponseEntity<Object> removePriceIntent(@PathVariable("intentId") Long intentId);

	@PostMapping("/purchase/add")
	public ResponseEntity<Object> addPurchase(@RequestBody String json);

	// для поиска по названию
	@GetMapping("/purchase/suggestions/{name}/{initiator}")
	public ResponseEntity<Object> getPurchaseSuggestions(@PathVariable("name") String name,
			@PathVariable("initiator") Long initiator);

	@GetMapping("/purchase/suggestions/{initiator}")
	public ResponseEntity<Object> getPurchaseSuggestionsByInitiator(@PathVariable("initiator") Long initiator);

	@GetMapping("/purchase/testAdd")
	public ResponseEntity<Object> testPurAdd();

	@GetMapping("/actions/bundle/get/{bundleId}/{page}/{pageSize}")
	public ResponseEntity<Object> getActionsByBundle(@PathVariable("bundleId") Long proposalId,
			@PathVariable("page") int page, @PathVariable("pageSize") Integer pageSize);

	@GetMapping("/purchase/all/page/{page}/{pageSize}")
	ResponseEntity<Object> getAllPurchaseByPage(@PathVariable(name = "page") int page,
			@PathVariable(name = "pageSize", required = false) Integer pageSize);

	@GetMapping("/purchase/payments/id/{id}/page/{page}/{pageSize}")
	ResponseEntity<Object> getPaymentsByPurchaseByPage(@PathVariable("id") Long purchaseId,
			@PathVariable("page") int page, @PathVariable("pageSize") Integer pageSize);

	@PostMapping("/payment/add")
	ResponseEntity<Object> addPayment(@RequestBody String json);

	@GetMapping("/actions/all/page/{page}/{pageSize}")
	public ResponseEntity<Object> getAllActionsByPage(@PathVariable(name = "page") int page,
			@PathVariable(name = "pageSize", required = false) Integer pageSize);

	@GetMapping("/actions/state/{state}/page/{page}/{pageSize}")
	public ResponseEntity<Object> getActionsByState(@PathVariable("state") int state,
			@PathVariable(name = "page") int page, @PathVariable(name = "pageSize", required = false) Integer pageSize);

	@GetMapping("/actions/report/1/{id}")
	public ResponseEntity<Object> createReportBuyersBySupplier(@PathVariable(name = "id") Long id);

	@GetMapping("/actions/report/2/{id}")
	public ResponseEntity<Object> createReportBuyersByGoods(@PathVariable(name = "id") Long id);

	@GetMapping({ "/actions/report/3/{supplierId}", "/actions/report/3/{supplierId}/download" })
	public ResponseEntity<Object> createReportDelivery(@PathVariable(name = "supplierId") Long supplierId);

	@GetMapping("/actions/cart/get/{memberId}")
	public ResponseEntity<Object> fetchMembersCart(@PathVariable(name = "memberId") Long memberId);

	@PutMapping("/actions/cart/put/{memberId}/{addressType}/{deliveryAddress}")
	public ResponseEntity<Object> createOrder(@PathVariable("memberId") Long memberId,
			@PathVariable("addressType") Integer addressType,
			@PathVariable(name = "deliveryAddress") String deliveryAddress,
			@RequestBody SortedMap<Long, List<Long>> data);

	@GetMapping("/actions/ordersByPartner/{partnerId}/{status}")
	public ResponseEntity<Object> fetchOrdersByPartner(@PathVariable("partnerId") Long partnerId,
			@PathVariable("status") Integer status);

	@GetMapping("/actions/ordersByCustomer/{memberId}/{status}")
	public ResponseEntity<Object> fetchOrdersByCustomer(@PathVariable("memberId") Long memberId,
			@PathVariable("status") Integer status);

	@GetMapping("/actions/ordersByNo/{orderId}")
	public ResponseEntity<Object> fetchOrdersByNo(@PathVariable("orderId") String orderId);

	@PutMapping("/actions/changeStatus/{id}/{status}")
	public ResponseEntity<Object> changeActionStatus(@PathVariable("id") Long id,
			@PathVariable("status") String status);

}
