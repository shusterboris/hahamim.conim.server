package application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import application.entities.CatItem;
import application.services.repositories.CatItemDAO;

@Service
public class CatItemServices {
	private int pageSize = 10;

	@Autowired
	private CatItemDAO cDAO;

	/**
	 * создает справочник
	 * 
	 * @param listRef
	 * @return
	 */
	public boolean createBundle(List<CatItem> listRef) {

		for (CatItem m : listRef) {
			List<CatItem> old = cDAO.findByItemKeyAndValueAndLanguage(m.getItemKey(), m.getValue(), m.getLanguage());
			if (old.isEmpty()) {
				try {
					m = cDAO.save(m);
					if (m == null)
						return false;

				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;

	}

	public CatItem create(CatItem ec) {
		List<CatItem> old = cDAO.findByItemKeyAndValueAndLanguage(ec.getItemKey(), ec.getValue(), ec.getLanguage());
		if (old.isEmpty()) {
			try {
				ec = cDAO.save(ec);

			} catch (Exception e) {
				return ec;
			}
		}

		return ec;

	}

	public CatItem update(CatItem ec) {
		try {
			ec = cDAO.save(ec);
			return ec;
		} catch (Exception e) {
			return ec;
		}
	}

	public List<CatItem> getAll(String language) {
		if (language == null)
			return cDAO.findAll();
		return cDAO.findByLanguage(language);
	}

	public CatItem getItemByValue(String key, String value, String language) {
		List<CatItem> items = getAll(language);
		return items.stream().filter((item) -> key.equals(item.getItemKey()) && value.equals(item.getValue()))
				.findFirst().orElse(null);

	}

	public Page<CatItem> getAllByPage(int page) {
		PageRequest request = PageRequest.of(page - 1, pageSize);
		return cDAO.findAll(request);
	}
}