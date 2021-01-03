package application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.entities.Member;
import application.services.repositories.MembersDAO;

@Service
public class ClientService {
	@Autowired
	private MembersDAO cDAO;
	
	public List<Member> findMembersAll(){
		return cDAO.findAll();
	}
	
	public boolean createMember(Member m) {
		try {
			return cDAO.save(m) != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Member getUser(String login) {
		Optional<Member> res = cDAO.findByLogin(login);
		return res.orElse(null);
	}
	public List<Member> getClients() {
		return cDAO.findAll();
	}
	
}
