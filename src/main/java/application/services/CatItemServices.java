package application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import application.entities.CatItem;
import application.services.repositories.CatItemDAO;

@Service
public class CatItemServices {
	private int pageSize = 15;

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
		if (page < 0)
			page = 0;
		PageRequest request = PageRequest.of(page, pageSize);
		return cDAO.findAll(request);
	}

	// для получения адреса в формате регион, город
	public ArrayList<String> getValueById(Long id) {
		ArrayList<String> ls = new ArrayList<String>();
		Optional<CatItem> ct;
		ct = cDAO.findById(id);
		if (ct.isPresent()) {
			if (ct.get().getParentKey() != null)
				ls.add(ct.get().getParentKey());
			ls.add(ct.get().getValue());
		}
		return ls;
	}

	public ArrayList<String> getItemsAsStrings(String itemKey, String lang) {
		ArrayList<String> ls = new ArrayList<String>();
		List<CatItem> res = cDAO.findByItemKeyAndLanguage(itemKey, lang);
		if (res.size() > 0) {
			for (CatItem item : res)
				if (item.getParentKey() == null || "".equals(item.getParentKey()))
					ls.add(item.getValue());
		}
		return ls;
	}

	public CatItem getItemByValue(String key, String value) {
		CatItem res=null;
		List<CatItem> items = cDAO.findByItemKeyAndValue(key, value);
		if (!items.isEmpty()) res=items.get(0);
		return res;
	}
}