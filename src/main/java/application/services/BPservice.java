package application.services;

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
		 BusinessPartner bp=new BusinessPartner("club", "club Conim Hahamim", "Bat-Yam");
		 if (pDAO.save(bp)==null) return false;
		 bp=new BusinessPartner("ARDIV", "factory ARDIV", "Crayiot");
		 if (pDAO.save(bp)==null)	return false;
		 
		 return true;
			
		} catch (Exception e) {
			return false;
		}
	}
}
