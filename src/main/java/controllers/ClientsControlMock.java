package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import entities.Person;
import entities.enums.ClientStatus;
import entities.enums.Gender;
import entities.enums.UserType;

public class ClientsControlMock implements ClientsControl {
	Long id = (long) 1;
	List<Person> clients = new ArrayList<Person>();

	@Override
	public ResponseEntity<Object> getAll() {
		return new ResponseEntity<Object>(clients, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> createClient(Person p) {
		p.setId(++id);
		clients.add(p);
		return new ResponseEntity<Object>(p, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> updateClient(Long id, Person p) {
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

	private void createStaff() {
		clients.add(createPerson("Борис", "Шустер", Gender.MALE, UserType.SUPERVISOR, "boris", "nothing"));
		clients.add(createPerson("Инна", "Шустер", Gender.FEMALE, UserType.SUPERVISOR, "inna", "nothing"));
		clients.add(createPerson("Владимир", "Олевский", Gender.MALE, UserType.MODERATOR, "vlad", "nothing"));
		clients.add(createPerson("Хаим", "Шапошник", Gender.MALE, UserType.SUPERVISOR, "haim", "nothing"));
	}

	private Person createPerson(String name, String lastName, Gender gender, UserType user, String login,
			String... others) {
		Person p = new Person();
		p.setFirstName(name);
		p.setLastName(lastName);
		p.setGender(gender.ordinal());
		p.setUserType(user);
		p.setLogin(login);
		p.setStatus(ClientStatus.ACTIVE);
		p.setPassword("123");
		p.setId(++id);
		return p;
	}

	public ClientsControlMock() {
		createStaff();
	}

	@Override
	public ResponseEntity<Object> getClientById(Long id) {
		Long cId = Long.valueOf(id);
		for (Person p : clients)
			if (p.getId() == cId)
				return new ResponseEntity<Object>(p, HttpStatus.OK);
		return new ResponseEntity<Object>("Not found", HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
