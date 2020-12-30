package application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.entities.CatItem;
import application.services.repositories.CatItemDAO;

@Service
public class CatItemServices {
	@Autowired
	private CatItemDAO cDAO;

	public boolean create(CatItem m) {
		
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
