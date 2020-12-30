package application.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.entities.Member;
import application.services.repositories.MembersDAO;
import proxies.Person;


@Service
public class ClientService {
	@Autowired
	private MembersDAO cDAO;
	
	public List<Member> findMembersAll(){
		return cDAO.findAll();
	}
	
	public boolean createMember(Member m) {
		
		try {
			if (cDAO.save(m)!=null) {
				
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public Member getUser(String login) {
		Optional<Member> res = cDAO.findByLogin(login);
		if (res.isEmpty()) return null;
		return res.get();
	}
	public List<Member> getClients() {
		return cDAO.findAll();
	}
	
}
