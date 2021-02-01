package application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import application.entities.Member;
import application.entities.security.User;
import application.services.repositories.MembersDAO;
import application.services.repositories.UserDAO;

@Service
public class ClientService {
	@Autowired
	private MembersDAO cDAO;
	@Autowired
	private UserDAO userDAO;

	public List<Member> findMembersAll() {
		return cDAO.findAll();
	}

	@Transactional
	public Member createMember(Member m) {
		try {
			Member nm = cDAO.save(m);
			String password = m.getPassword();
			m.setPassword("");
			User user = new User(m.getLogin(), password);
			if (!password.startsWith("$")) {
				BCryptPasswordEncoder userPasswordEncoder = new BCryptPasswordEncoder();
				password = userPasswordEncoder.encode(password);
				user.setPassword(password);
			}
			userDAO.save(user);
			return nm;
		} catch (Exception e) {
			return null;
		}
	}

	public Member getUser(String login) {
		Optional<Member> res = cDAO.findByLogin(login);
		return res.orElse(null);
	}

	public Member getMemberById(Long id) {
		try {
			Optional<Member> nm = cDAO.findById(id);
			return nm.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	public String isUnique(Member em) {
		Optional<Member> m = cDAO.findByLogin(em.getLogin());
		if (m.isPresent())
			return "login exists";
		if (em.getEmail() != null) {
			if (!em.getEmail().equalsIgnoreCase("")) {
				m = cDAO.findByEmail(em.getEmail());
				if (m.isPresent())
					return "email exists";
			}
		}
		m = cDAO.findByPhone(em.getPhone());
		if (m.isPresent())
			return "phone exists";

		UserDetails user = userDAO.findByUsername(em.getLogin());
		if (user != null)
			return "login exists";
		return "";
	}

}
