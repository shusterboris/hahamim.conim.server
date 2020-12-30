package application.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import application.services.CatItemServices;
import application.services.MockService;
import proxies.CatItem;

@RestController
public class CatalogsControlImpl implements CatalogsControl {
	private MockService mService = new MockService();
	@Autowired
	private CatItemServices serv;
	@Override
	public ResponseEntity<Object> getItemsByKey(String key, String language) {
		try {
			List<CatItem> res = mService.getRootCatsByCategory(key, language);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getItemsByParentKey(String parentKey, String language) {
		try {
			CatItem parentItem = mService.getCatByValue(parentKey,language);
			List<CatItem> res = mService.getChildCatsByCategory(parentItem.getKey(), parentKey, language);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getAllItems(String language) {
		return new ResponseEntity<Object>(mService.getAllCategories(language),HttpStatus.OK);
	}

	
	@Override
	public ResponseEntity<Object> getItemStringByKey(String key, String language) {
		try {
			List<CatItem> qres = mService.getRootCatsByCategory(key, language);
			List<String> res = new ArrayList<>();
			for(CatItem item : qres)
				if (item.getParentId() == 0)
					res.add(item.toString());
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getAllItemsByKey(String key, String language) {
		try {
			List<CatItem> qres = mService.getCatsByCategory(key, language);
			return new ResponseEntity<Object>(qres, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Override
	public ResponseEntity<Object> getItemStringByParentKey(String parentKey, String language) {
		try {
			CatItem parentItem = mService.getCatByValue(parentKey,language);
			List<CatItem> qres = mService.getChildCatsByCategory(parentItem.getKey(), parentKey, language);
			List<String> res = new ArrayList<>();
			for(CatItem item : qres)
				res.add(item.toString());
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getRegionsList(String language) {
		try {
			List<CatItem> res = mService.getRegionsList(language);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getRegionsStrings(String language) {
		try {
			List<String> res = mService.getRegionsString(language);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getSetllmetsList(String region, String language) {
		try {
			List<CatItem> res = mService.getSettlmentsList(region, language);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> getSetllmetsStrings(String region, String language) {
		try {
			List<String> res = mService.getSettlmentsStrings(region, language);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Server error:".concat(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Override
	public  ResponseEntity<Object> create(){
	
	/*result.add(new CatItem(id++, "Country.Regions", "RU", "Северный", 1000, (long) 0));
	result.add(new CatItem(id++, "Country.Regions", "RU", "Южный", 1000, (long) 0));
	haifaRegion = new CatItem(id++, "Country.Regions", "RU", "Хайфа",  1000, (long) 0);
	result.add(haifaRegion);
	result.add(new CatItem(id++, "Country.Regions", "RU", "Центральный",  1000, (long) 0));
	result.add(new CatItem(id++, "Country.Regions", "RU", "Иудея и Самария",  1000, (long) 0));
	result.add(new CatItem(id++, "Country.Regions", "RU", "Тель-Авив",  1000, (long) 0));
	result.add(new CatItem(id++, "Country.Regions", "RU", "Иерусалим",  1000, (long) 0));
	result.add(new CatItem(id++, "Country.Regions", "EN", "North",  1000, (long) 0));
	result.add(new CatItem(id++, "Country.Regions", "EN", "South",  1000, (long) 0));
	cat=new CatItem((long) 0, "Country.Regions", "EN", "North",  0, (long) 0);*/
	application.entities.CatItem ec=new application.entities.CatItem("Country.Regions","North","EN");
	serv.create(ec);
	ec=new application.entities.CatItem("Country.Regions","North","RU");
	serv.create(ec);
	ec=new application.entities.CatItem("Country.Regions","Haifa","EN");
	serv.create(ec);
	ec=new application.entities.CatItem("Country.Regions","Haifa","RU");
	serv.create(ec);
	
	return new ResponseEntity<Object>("", HttpStatus.OK);
	}
}
