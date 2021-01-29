package application.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import application.entities.PageResponse;
import application.services.CatItemServices;
import proxies.CatItem;

@RestController
public class CatalogsControlImpl implements CatalogsControl {
	// private MockService mService = new MockService();
	@Autowired
	private CatItemServices serv;

	@Override
	public ResponseEntity<Object> getAllItems(String language) {
		// return new
		// ResponseEntity<Object>(mService.getAllCategories(language),HttpStatus.OK);
		List<CatItem> res = new ArrayList<CatItem>();
		List<application.entities.CatItem> eAll = serv.getAll(language);
		if (eAll.isEmpty())
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		for (application.entities.CatItem ce : eAll)
			res.add(catItemToProxy(ce));
		return new ResponseEntity<Object>(res, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Object> getAllItemsPage(int page, String language) {
		Page<application.entities.CatItem> itemList = serv.getAllByPage(page);
		@SuppressWarnings("rawtypes")
		PageResponse result = new PageResponse(itemList.getContent(), itemList.getTotalPages());
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	private CatItem catItemToProxy(application.entities.CatItem ce) {
		CatItem cp = new CatItem(ce.getId(), ce.getItemKey(), ce.getLanguage(), ce.getValue());
		cp.setAddValue(ce.getAddValue());
		cp.setParentKey(ce.getParentKey());
		return cp;
	}

	@Override
	public ResponseEntity<Object> createAll() {
		List<application.entities.CatItem> ref = new ArrayList<application.entities.CatItem>();

		// создаем пачку верхнего уровня
		ref.add(new application.entities.CatItem("Country.Regions", "North", "EN", null));
		ref.add(new application.entities.CatItem("Country.Regions", "Север", "RU", null));
		ref.add(new application.entities.CatItem("Country.Regions", "Haifa", "EN", null));
		ref.add(new application.entities.CatItem("Country.Regions", "Хайфа", "RU", null));
		ref.add(new application.entities.CatItem("Country.Regions", "Tel-Aviv", "EN", null));
		ref.add(new application.entities.CatItem("Country.Regions", "Тель-Авив", "RU", null));
		ref.add(new application.entities.CatItem("Measures", "kg", "EN", null));
		ref.add(new application.entities.CatItem("Measures", "кг", "RU", null));
		ref.add(new application.entities.CatItem("Measures", "piece", "EN", null));
		ref.add(new application.entities.CatItem("Measures", "упаковка", "RU", null));
		ref.add(new application.entities.CatItem("Measures", "liter", "EN", null));
		ref.add(new application.entities.CatItem("Measures", "литр", "RU", null));
		ref.add(new application.entities.CatItem("Goods.Category", "Food", "EN", null));
		ref.add(new application.entities.CatItem("Goods.Category", "Продукты", "RU", null));
		serv.createBundle(ref);
		// meat
		application.entities.CatItem ec = new application.entities.CatItem("Goods.Category", "Meat", "EN", "Food");
		ec.setAddValue("Salami.png");
		serv.create(ec);
		ec = new application.entities.CatItem("Goods.Category", "Мясо", "RU", "Продукты");
		ec.setAddValue("Salami.png");
		serv.create(ec);
		// delic
		ec = new application.entities.CatItem("Goods.Category", "Delicacies", "EN", "Food");
		ec.setAddValue("seafoods.png");
		serv.create(ec);
		ec = new application.entities.CatItem("Goods.Category", "Деликатесы", "RU", "Продукты");
		ec.setAddValue("seafoods.png");
		serv.create(ec);
		// drinks
		ec = new application.entities.CatItem("Goods.Category", "Drinks", "EN", "Food");
		ec.setAddValue("wine.png");
		serv.create(ec);
		ec = new application.entities.CatItem("Goods.Category", "Напитки", "RU", "Продукты");
		ec.setAddValue("wine.png");
		serv.create(ec);
		// города
		ec = new application.entities.CatItem("Country.Regions", "Kiriat-Yam", "EN", "Haifa");
		serv.create(ec);
		ec = new application.entities.CatItem("Country.Regions", "Кирьят-Ям", "RU", "Найфа");
		serv.create(ec);
		ec = new application.entities.CatItem("Country.Regions", "Bat-Yam", "EN", "Tel-Aviv");
		serv.create(ec);
		ec = new application.entities.CatItem("Country.Regions", "Бат-Ям", "RU", "Тель-Авив");
		serv.create(ec);
		ec = new application.entities.CatItem("Country.Regions", "Naharya", "EN", "North");
		serv.create(ec);
		ec = new application.entities.CatItem("Country.Regions", "Нагария", "RU", "Север");
		serv.create(ec);
		/*
		 * addToParentwithImage(item, "Goods.Category", "Meat", "EN","Salami.png");
		 * addToParentwithImage(item, "Goods.Category", "Vegetables and fruits",
		 * "EN","fruits.png"); addToParentwithImage(item, "Goods.Category",
		 * "Delicacies", "EN","seafoods.png"); addToParentwithImage(item,
		 * "Goods.Category", "Drinks", "EN","wine.png"); addToParentwithImage(item,
		 * "Goods.Category", "Sweets", "EN","sw.png"); addToParentwithImage(item,
		 * "Goods.Category", "Cheese", "EN","cheese.png");
		 * 
		 * item = new CatItem(id++, "Goods.Category", "EN", "Electronic devices", 1000,
		 * (long) 0); catByName.put("Goods.Category" + "-" + item.getValue(), item);
		 * addToParent(item, "Goods.Category", "Music and audio", "EN");
		 * addToParent(item, "Goods.Category", "TV and computers", "EN");
		 * addToParent(item, "Goods.Category", "Mobile devices", "EN");
		 * 
		 * item = new CatItem(id++, "Goods.Category", "EN", "Goods for home", 1000,
		 * (long) 0); catByName.put("Goods.Category" + "-" + item.getValue(), item);
		 * addToParent(item, "Goods.Category", "Kitchens", "EN"); addToParent(item,
		 * "Goods.Category", "Bedrooms", "EN"); addToParent(item, "Goods.Category",
		 * "Toilets and Baths", "EN");
		 */
		return new ResponseEntity<Object>("", HttpStatus.OK);
	}
	/*
	 * @Override public ResponseEntity<Object> getItemsByKey(String key, String
	 * language) { try { List<CatItem> res = mService.getRootCatsByCategory(key,
	 * language); return new ResponseEntity<Object>(res, HttpStatus.OK); } catch
	 * (Exception e) { return new
	 * ResponseEntity<Object>("Server error:".concat(e.getMessage()),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 * @Override public ResponseEntity<Object> getItemsByParentKey(String parentKey,
	 * String language) { try { CatItem parentItem =
	 * mService.getCatByValue(parentKey,language); List<CatItem> res =
	 * mService.getChildCatsByCategory(parentItem.getKey(), parentKey, language);
	 * return new ResponseEntity<Object>(res, HttpStatus.OK); } catch (Exception e)
	 * { return new ResponseEntity<Object>("Server error:".concat(e.getMessage()),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 */

	/*
	 * @Override public ResponseEntity<Object> getItemStringByKey(String key, String
	 * language) { try { List<CatItem> qres = mService.getRootCatsByCategory(key,
	 * language); List<String> res = new ArrayList<>(); for(CatItem item : qres) if
	 * (item.getParentId() == 0) res.add(item.toString()); return new
	 * ResponseEntity<Object>(res, HttpStatus.OK); } catch (Exception e) { return
	 * new ResponseEntity<Object>("Server error:".concat(e.getMessage()),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 * @Override public ResponseEntity<Object> getAllItemsByKey(String key, String
	 * language) { try { List<CatItem> qres = mService.getCatsByCategory(key,
	 * language); return new ResponseEntity<Object>(qres, HttpStatus.OK); } catch
	 * (Exception e) { return new
	 * ResponseEntity<Object>("Server error:".concat(e.getMessage()),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 * 
	 * @Override public ResponseEntity<Object> getItemStringByParentKey(String
	 * parentKey, String language) { try { CatItem parentItem =
	 * mService.getCatByValue(parentKey,language); List<CatItem> qres =
	 * mService.getChildCatsByCategory(parentItem.getKey(), parentKey, language);
	 * List<String> res = new ArrayList<>(); for(CatItem item : qres)
	 * res.add(item.toString()); return new ResponseEntity<Object>(res,
	 * HttpStatus.OK); } catch (Exception e) { return new
	 * ResponseEntity<Object>("Server error:".concat(e.getMessage()),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 * @Override public ResponseEntity<Object> getRegionsList(String language) { try
	 * { List<CatItem> res = mService.getRegionsList(language); return new
	 * ResponseEntity<Object>(res, HttpStatus.OK); } catch (Exception e) { return
	 * new ResponseEntity<Object>("Server error:".concat(e.getMessage()),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 * @Override public ResponseEntity<Object> getRegionsStrings(String language) {
	 * try { List<String> res = mService.getRegionsString(language); return new
	 * ResponseEntity<Object>(res, HttpStatus.OK); } catch (Exception e) { return
	 * new ResponseEntity<Object>("Server error:".concat(e.getMessage()),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 * @Override public ResponseEntity<Object> getSetllmetsList(String region,
	 * String language) { try { List<CatItem> res =
	 * mService.getSettlmentsList(region, language); return new
	 * ResponseEntity<Object>(res, HttpStatus.OK); } catch (Exception e) { return
	 * new ResponseEntity<Object>("Server error:".concat(e.getMessage()),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 * @Override public ResponseEntity<Object> getSetllmetsStrings(String region,
	 * String language) { try { List<String> res =
	 * mService.getSettlmentsStrings(region, language); return new
	 * ResponseEntity<Object>(res, HttpStatus.OK); } catch (Exception e) { return
	 * new ResponseEntity<Object>("Server error:".concat(e.getMessage()),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } }
	 * 
	 */
}
