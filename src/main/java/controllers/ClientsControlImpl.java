package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import entities.Member;
import entities.Person;
import services.MockService;

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
		// clients = mService.createStaff();
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

}
