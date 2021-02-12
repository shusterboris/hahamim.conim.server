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
			/*
			 * temporary BusinessPartner bp=new BusinessPartner("club",
			 * "club Conim Hahamim", "Bat-Yam"); if (pDAO.save(bp)==null) return false;
			 * bp=new BusinessPartner("ARDIV", "factory ARDIV", "Crayiot"); if
			 * (pDAO.save(bp)==null) return false;
			 */
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

	public BusinessPartner findbyId(Long id) {
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

	public List<BusinessPartner> findByNameLike(String fullName) {
		return pDAO.findByFullNameContaining(fullName);
	}
}
