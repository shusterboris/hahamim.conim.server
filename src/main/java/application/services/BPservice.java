package application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.entities.BusinessPartner;
import application.services.repositories.PartnerDAO;

@Service
public class BPservice {
	@Autowired
	private PartnerDAO pDAO;

	public boolean createAll() {
		try {
			BusinessPartner bp = new BusinessPartner();
			bp.setFullName("club Conim Hahamim");
			if (pDAO.save(bp) == null)
				return false;
			bp = new BusinessPartner();
			bp.setFullName("factory ARDIV");
			if (pDAO.save(bp) == null)
				return false;
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public BusinessPartner findById(Long id) {
		try {
			Optional<BusinessPartner> b = pDAO.findById(id);
			if (b == null)
				return null;
			return b.get();
		} catch (Exception e) {
			return null;
		}
	}

	public List<BusinessPartner> findAll() {
		try {
			List<BusinessPartner> lb = pDAO.findAll();
			if (lb == null)
				return null;
			return lb;
		} catch (Exception e) {
			return null;
		}
	}

	public List<BusinessPartner> findByFullNameLike(String fullName) {
		return pDAO.findByFullNameContaining(fullName);
	}

	public BusinessPartner save(BusinessPartner bp) {
		try {
			return pDAO.save(bp);
		} catch (Exception e) {
			return null;
		}
	}

	// связь кто заказывал у партнера
	public boolean findMembersWhoOrder(Long p, Long m) {
		try {
			List<Object> res = pDAO.findMember(p, m);
			if (res.size() > 0)
				return true;
		} catch (Exception e) {

		}
		return false;
	}

	public boolean saveMemberPartnerRelation(Long bp, Long m) {
		try {
			pDAO.saveMemberPartnerRelation(bp, m);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
