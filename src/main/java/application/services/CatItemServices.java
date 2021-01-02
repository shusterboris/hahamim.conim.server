package application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.entities.CatItem;
import application.services.repositories.CatItemDAO;

@Service
public class CatItemServices {
	@Autowired
	private CatItemDAO cDAO;

	public boolean create(List<CatItem> listRef) {
		Long itemId=(long) 0;
		for (CatItem m :listRef){
			try {
				if (itemId==0) {
					CatItem newM = cDAO.save(m);
					itemId=newM.getId();		
				}
				m.setItemId(itemId);
				cDAO.save(m);

			} catch (Exception e) {
				return false;
			}
			
		}
		return true;
	}
}