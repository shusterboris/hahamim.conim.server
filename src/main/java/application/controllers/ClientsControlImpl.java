package application.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Utils.LocalDateJsonAdapter;
import application.entities.CatItem;
import application.entities.Member;
import application.entities.PageResponse;
import application.services.BPservice;
import application.services.CatItemServices;
import application.services.ClientService;
import enums.ClientStatus;
import enums.UserType;
import exceptions.EntityNotFound;
import lombok.Getter;
import lombok.Setter;

@RestController
@Getter
@Setter
public class ClientsControlImpl implements ClientsControl {
	// private MockService mService = new MockService();
	Pattern phoneNumPattern = Pattern.compile("\\+{0,1}\\d+");
	@Autowired
	private ClientService cserv;
	@Autowired
	private BPservice bseratev;
	@Autowired
	private CatItemServices catService;
	Long id = (long) 1;
	List<proxies.Member> clients = new ArrayList<proxies.Member>();

	@Override
	public ResponseEntity<Object> getAll() {
		Iterable<Member> lme = cserv.findMembersAll();
		for (Member me : lme)
			clients.add(convertMemberToProxy(me));
		return new ResponseEntity<Object>(clients, HttpStatus.OK);
	}

	private Member memberProxyToEntity(proxies.Member pm) {
		// пока без региона
		Member em = new Member();
		if (pm.getId() != 0) {
			em.setId(pm.getId());
		}
		em.setFirstName(pm.getFirstName());
		em.setLastName(pm.getLastName());
		em.setEmail(pm.getEmail());
		em.setPhone(pm.getPhone());
		em.setLevel(0);
		em.setRate(pm.getRate());
		em.setStatus(pm.getStatus().ordinal());
		em.setType(pm.getUserType().ordinal());
		em.setLogin(pm.getLogin());
		em.setPassword(pm.getPassword());
		em.setGender(pm.getGender());
		em.setBirthday(pm.getBirthday());
		em.setNote(pm.getNote());
		List<String> regionList = pm.getRegions();
		if (regionList != null && regionList.size() > 0) {
			CatItem item = catService.getItemByValue("Country.Regions", regionList.get(regionList.size() - 1), null);
			if (item != null)
				em.setRegion(item.getId());
		}
		if (pm.getPartnerId() != null)
			em.setPartner(pm.getPartnerId());
		return em;
	}

	@Override
	public ResponseEntity<Object> createClient(String json) {
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter()).create();
		proxies.Member pm = gson.fromJson(json, proxies.Member.class);
		Member em = memberProxyToEntity(pm);
		// проверка на логин телефон и мейл
		String res = cserv.isUnique(em);
		if (!res.equalsIgnoreCase(""))
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		em = cserv.createMember(em);
		if (em != null) {
			pm = convertMemberToProxy(em);
			return new ResponseEntity<Object>(pm, HttpStatus.OK);
		} else
			return new ResponseEntity<Object>("", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> getClientById(Long id) {
		Member me = cserv.getMemberById(id);
		if (me == null)
			return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
		proxies.Member res = convertMemberToProxy(me);
		return new ResponseEntity<Object>(res, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> userLogin(String user) {
		try {
			Member me = cserv.getUser(user);
			if (me == null)
				return new ResponseEntity<Object>(new EntityNotFound(), HttpStatus.OK);
			// сделать прокси объект
			proxies.Member p = convertMemberToProxy(me);
			return new ResponseEntity<Object>(p, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Invalid user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private proxies.Member convertMemberToProxy(Member me) {
		ClientStatus[] st = ClientStatus.values();
		UserType[] u = UserType.values();
		proxies.Member p = new proxies.Member();
		p.setId(me.getId());
		p.setFirstName(me.getFirstName());
		p.setLastName(me.getLastName());
		p.setGender(me.getGender());
		p.setUserType(u[me.getType()]);
		p.setStatus(st[me.getStatus()]);
		p.setPartnerId(me.getPartner());
		p.setPhone(me.getPhone());
		if (me.getRate() == null)
			me.setRate((float) 2);
		p.setRate(me.getRate());
		p.setLogin(me.getLogin());
		p.setEmail(me.getEmail());
		p.setNote(me.getNote());
		p.setLevel(me.getLevel() != null ? me.getLevel() : 0);
		p.setBirthday(me.getBirthday());
		p.setAuthorities(me.getAutorityList());
		if (me.getRegion() != null)
			p.setRegions(catService.getValueById(me.getRegion()));
		return p;
	}

	@Override
	public ResponseEntity<Object> createAll() {
		Member em = new Member();
		em.setFirstName("May");
		em.setLastName("Levi");
		em.setPhone("+972556664444");
		em.setLogin("may");
		em.setPassword("123");
		// em.setPartner(null);
		em.setType(0);
		em.setStatus(2);
		em.setLevel(0);
		Member res = cserv.createMember(em);
		if (res == null)
			return new ResponseEntity<Object>("", HttpStatus.INTERNAL_SERVER_ERROR);

		em = new Member();
		em.setFirstName("Aron");
		em.setLastName("Kogan");
		em.setPhone("+972556664442");
		em.setLogin("aron");
		em.setPassword("123");
		// em.setPartner(null);
		em.setType(1);
		em.setStatus(2);
		em.setLevel(0);
		res = cserv.createMember(em);
		if (res == null)
			return new ResponseEntity<Object>("", HttpStatus.INTERNAL_SERVER_ERROR);

		em = new Member();
		em.setFirstName("Vladimir");
		em.setLastName("Olevski");
		em.setPhone("+972556664477");
		em.setLogin("vova");
		em.setPassword("123");
		// em.setPartner(null);
		em.setType(2);
		em.setStatus(2);
		em.setLevel(0);
		res = cserv.createMember(em);
		if (res == null)
			return new ResponseEntity<Object>("", HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> updateClient(String json) {
		// proxies.Member pm = new Gson().fromJson(json, proxies.Member.class);
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter().nullSafe())
				.create();
		proxies.Member pm = gson.fromJson(json, proxies.Member.class);
		Member em = memberProxyToEntity(pm);
		em.setId(pm.getId());
		// проверка на логин телефон и мейл
		String res = cserv.isUnique(em);
		if (!res.equalsIgnoreCase(""))
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		em = cserv.updateMember(em);
		if (em != null) {
			pm = convertMemberToProxy(em);
			return new ResponseEntity<Object>(pm, HttpStatus.OK);
		} else
			return new ResponseEntity<Object>("Can't save member, DAO error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<Object> getPaymentsByClient(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Object> getAllByPage(int pageNo, Integer pageSize) {
		Page<Member> itemList = cserv.findMembersAllByPage(pageNo, pageSize);
		List<proxies.Member> proxies = new ArrayList<>();
		for (Member member : itemList.getContent()) {
			proxies.Member proxy = convertMemberToProxy(member);
			proxies.add(proxy);
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageResponse result = new PageResponse(proxies, itemList.getTotalPages(), itemList.getTotalElements());
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> fetchByStringFilterByPage(String query, int page, Integer pageSize) {
		Page<Member> itemList;
		if (phoneNumPattern.matcher(query).lookingAt())
			itemList = cserv.fetchByPhoneContainsByPage(query, page, pageSize);
		else
			itemList = cserv.fetchByStringFilterByPage(query, page, pageSize);
		List<proxies.Member> proxies = new ArrayList<>();
		for (Member member : itemList.getContent()) {
			proxies.Member proxy = convertMemberToProxy(member);
			proxies.add(proxy);
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageResponse result = new PageResponse(proxies, itemList.getTotalPages(), itemList.getTotalElements());
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

}
