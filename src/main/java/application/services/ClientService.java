package application.services;

import java.math.BigInteger;
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
			if (cDAO.save(m)!=null) {
				
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
}
