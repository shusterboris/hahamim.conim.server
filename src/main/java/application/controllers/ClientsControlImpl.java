package application.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import application.services.ClientService;
import application.services.MockService;
import exceptions.EntityNotFound;
import application.entities.Member;
import application.entities.Person;
import enums.ClientStatus;
import enums.UserType;

@RestController
public class ClientsControlImpl implements ClientsControl {
	private MockService mService = new MockService();
	@Autowired
	private ClientService cserv;
	Long id = (long) 1;
	List<proxies.Member> clients = new ArrayList<proxies.Member>();

	@Override
	public ResponseEntity<Object> getAll() {
		
		clients = mService.getClients();
		return new ResponseEntity<Object>(clients, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> createClient(String json) {
		proxies.Member pm = new Gson().fromJson(json, proxies.Member.class);
		Member em=new Member();
		em.setFirstName(pm.getFirstName());
		em.setLastName(pm.getLastName());
		em.setEmail(pm.getEmail());
		em.setPhone(pm.getPhone());
		em.setLevel(0);
		em.setStatus(0);
		em.setType(0);
		em.setLogin(pm.getLogin());
		em.setPassword(pm.getPassword());
		boolean res = cserv.createMember(em);
		//добавить проверку на телефон и мейл
		//p.setId(++id);
		//clients.add(p);
		if (res) {
		return new ResponseEntity<Object>("", HttpStatus.OK);
		} else
			return new ResponseEntity<Object>("", HttpStatus.INTERNAL_SERVER_ERROR);	
	}

	@Override
	public ResponseEntity<Object> updateClient(Long id, proxies.Member p) {
		int i = 0;
		for (proxies.Member pers : clients) {
			if (pers.getId() == id) {
				clients.remove(i);
				clients.add(p);
				break;
			}
			i++;
		}
		return new ResponseEntity<Object>(clients.get(i), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getClientById(Long id) {
		Long cId = Long.valueOf(id);
		for (proxies.Person p : clients)
			if (p.getId() == cId)
				return new ResponseEntity<Object>(p, HttpStatus.OK);
		return new ResponseEntity<Object>("Not found", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	
	@Override
	public ResponseEntity<Object> getStaff() {
		try {
			List<proxies.Member> res = mService.getClubStaff();
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * @Override public ResponseEntity<Object> getMembers() { try {
	 * List<proxies.Member> res = mService.getClubMembers(); return new
	 * ResponseEntity<Object>(res, HttpStatus.OK); } catch (Exception e) { return
	 * new ResponseEntity<Object>("Server error:".concat(e.getMessage()),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */

	@Override
	public ResponseEntity<Object> userLogin(proxies.Person user) {
		try {
			proxies.Person member = mService.getUser(user.getLogin());
			if (member == null)
				return new ResponseEntity<Object>(new EntityNotFound(),HttpStatus.OK);
			return new ResponseEntity<Object>(member, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("Invalid user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public ResponseEntity<Object> getPartnerById(Long id) {
		try {
			proxies.Member member = mService.getPartnerStaffById(id);
			if (member == null)
				return new ResponseEntity<Object>(new EntityNotFound(),HttpStatus.OK);
			return new ResponseEntity<Object>(member, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("Invalid partner staff, id "+String.valueOf(id), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
