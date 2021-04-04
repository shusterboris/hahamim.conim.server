package application.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import application.entities.BusinessPartner;
import application.entities.CatItem;
import application.entities.Delivery;
import application.entities.Member;
import application.entities.security.Authority;
import application.entities.security.User;
import application.services.repositories.MembersDAO;
import application.services.repositories.PartnerDAO;
import application.services.repositories.UserDAO;

@Service
public class ClientService {
	@Autowired
	private MembersDAO cDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PartnerDAO pDAO;
	@Autowired
	private CatItemServices catServ;

	public Iterable<Member> findMembersAll() {
		return cDAO.findAll();
	}

	public Page<Member> findMembersAllByPage(int pageNo, int pageSize) {
		if (pageNo < 0)
			pageNo = 0;
		PageRequest request = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
		return cDAO.findAll(request);
	}

	public Page<Member> fetchByStringFilterByPage(String queryStr, int pageNo, int pageSize) {
		if (pageNo < 0)
			pageNo = 0;
		PageRequest request = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
		Page<Member> result = cDAO.findMemberContainsValue("%" + queryStr + "%", request);
		return result;
	}

	public Page<Member> fetchByPhoneContainsByPage(String queryStr, int pageNo, int pageSize) {
		if (pageNo < 0)
			pageNo = 0;
		PageRequest request = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "id"));
		return cDAO.findByPhoneContaining(queryStr, request);
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

	public Member updateMember(Member m) {
		try {
			Optional<Member> res = cDAO.findById(m.getId());
			if (res.isPresent()) {
				Member nm = res.get();
				m.setCreated(nm.getCreated());
				m.setModified(nm.getModified());
				m.setVersion(nm.getVersion());
			}
			m = cDAO.save(m);
			return m;
		} catch (Exception e) {
			return null;
		}
	}

	public Member getUser(String login) {
		Optional<Member> res = cDAO.findByLogin(login);
		if (!res.isPresent())
			return null;
		User user = userDAO.findByUsername(res.get().getLogin());
		Member member = res.get();
		if (user == null)
			return null;
		if (user.getAuthorities() != null && user.getAuthorities().size() != 0) {
			String s = "";
			for (Authority auth : user.getAuthorities()) {
				s = ("".equals(s)) ? auth.toString() : (s.concat(" ").concat(auth.toString()));
			}
			member.setAutorityList(s);
		} else
			member.setAutorityList(null);
		return member;
	}

	public Member getMemberById(Long id) {
		try {
			Optional<Member> nm = cDAO.findById(id);
			return nm.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	private boolean theSameRecord(Member current, Optional<Member> foundRec) {
		Member foundMember = foundRec.get();
		return current.getId() == foundMember.getId();
	}

	public String isUnique(Member em) {
		Optional<Member> m = cDAO.findByLogin(em.getLogin());
		if (m.isPresent() && !theSameRecord(em, m))
			return "errMsg.loginIsNotUnique";
		if (em.getEmail() != null) {
			if (!em.getEmail().equalsIgnoreCase("")) {
				m = cDAO.findByEmail(em.getEmail());
				if (m.isPresent() && !theSameRecord(em, m))
					return "errMsg.emailIsNotUnique";
			}
		}
		m = cDAO.findByPhone(em.getPhone());
		if (m.isPresent() && !theSameRecord(em, m))
			return "errMsg.phoneIsNotUnique";

		UserDetails user = userDAO.findByUsername(em.getLogin());
		if (user != null && !user.getUsername().equals(em.getLogin()))
			return "loginIsNotUnique";
		return "";
	}

	public Page<Member> fetchByTelegram(String query) {
		PageRequest request = PageRequest.of(0, 5);
		Page<Member> res = cDAO.findByTelegramContaining(query, request);
		return res;
	}

	public Set<Member> findMembersByPartner(Long id) {
		Optional<BusinessPartner> p = pDAO.findById(id);
		if (p.isPresent()) {
			return p.get().getMember();
		}
		return null;
	}

	public Member findMemberById(Long id) {
		return cDAO.findById(id).orElse(null);
	}

	public Member createMemberFromTelegram(String firstName, String lastName, String phone, String tnumber,
			String address) {
		Member m = new Member();
		// m.setporder.getMember().getPreferableAddress()
		CatItem region = catServ.getItemByValue("Country.Regions", address);
		if (region != null)
			m.setRegion(region.getId());
		m.setFirstName(firstName);
		m.setLastName(lastName);
		m.setPhone(phone);
		m.setLogin(phone);
		m.setPassword(firstName + lastName);
		m.setTelegram(tnumber);
		m.setType(1);
		m.setStatus(2);
		m.setLevel(0);
		m = addDelivery(address, m);
		m = createMember(m);
		return m;
	}

	public Member addDelivery(String address, Member m) {
		Set<Delivery> dd = m.getDelivery();
		if (dd == null)
			dd = new HashSet<Delivery>();
		Delivery d = new Delivery();
		d.setStreetAddress(address);
		d.setMember(m);
		dd.add(d);
		m.setDelivery(dd);
		return m;
	}
}
