package application.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import application.services.MockService;
import exceptions.EntityNotFound;
import proxies.Member;
import proxies.Person;


public class ClientsControlImpl implements ClientsControl {
	private MockService mService = new MockService();
	Long id = (long) 1;
	List<Member> clients = new ArrayList<Member>();

	@Override
	public ResponseEntity<Object> getAll() {
		clients = mService.getClients();
		return new ResponseEntity<Object>(clients, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> createClient(Member p) {
		p.setId(++id);
		clients.add(p);
		return new ResponseEntity<Object>(p, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> updateClient(Long id, Member p) {
		int i = 0;
		for (Person pers : clients) {
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
		for (Person p : clients)
			if (p.getId() == cId)
				return new ResponseEntity<Object>(p, HttpStatus.OK);
		return new ResponseEntity<Object>("Not found", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	public ClientsControlImpl() {
		
	}

	@Override
	public ResponseEntity<Object> getStaff() {
		try {
			List<Member> res = mService.getClubStaff();
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getMembers() {
		try {
			List<Member> res = mService.getClubMembers();
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> userLogin(Person user) {
		try {
			Person member = mService.getUser(user.getLogin());
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
			Member member = mService.getPartnerStaffById(id);
			if (member == null)
				return new ResponseEntity<Object>(new EntityNotFound(),HttpStatus.OK);
			return new ResponseEntity<Object>(member, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("Invalid partner staff, id "+String.valueOf(id), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
