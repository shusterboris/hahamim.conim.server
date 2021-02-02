package application.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import application.ApplicationSettings;
import enums.ClientStatus;
import enums.Gender;
import enums.MemberStatus;
import enums.UserType;
import proxies.Address;
import proxies.BusinessPartner;
import proxies.CatItem;
import proxies.Contact;
import proxies.Member;
import proxies.Person;
import proxies.PriceProposal;
import proxies.Proposal;
import proxies.Store;

public class MockService {
	private Long id = (long) 10000;
	private List<CatItem> categories = new ArrayList<CatItem>();
	private Map<String, CatItem> catByName = new HashMap<String, CatItem>();
	private Map<Long, CatItem> catById = new HashMap<Long, CatItem>();
	List<Member> clients = new ArrayList<Member>();
	List<Proposal> proposals = new ArrayList<>();
	List<CatItem> measures = new ArrayList<>();
	List<Proposal> actions = new ArrayList<>();
	List<BusinessPartner> partners = new ArrayList<>();
	List<PriceProposal> intents = new ArrayList<>();
	private Random random = new Random();
	private BusinessPartner partner;
	private BusinessPartner club;
	private CatItem haifa;
	private CatItem haifaRegion;

	public List<CatItem> getCatsByCategory(String key, String language) {
		if (language == null)
			language = ApplicationSettings.getDefaultLanguage();
		final String lang = language;
		List<CatItem> items = new ArrayList<CatItem>();
		catByName.forEach((k, value) -> {
			if (k.startsWith(key) && lang.equalsIgnoreCase(value.getLanguage()))
				items.add(value);
		});
		return items;
	}

	public List<CatItem> getRootCatsByCategory(String key, String language) {
		if (language == null)
			language = ApplicationSettings.getDefaultLanguage();
		final String lang = language;
		List<CatItem> items = new ArrayList<CatItem>();
		catByName.forEach((k, value) -> {
			if (k.startsWith(key) && ("".equals(value.getParentKey()) && lang.equalsIgnoreCase(value.getLanguage())))
				items.add(value);
		});
		return items;
	}

	public List<CatItem> getChildCatsByCategory(String key, Object parentObj, String language) {
		List<CatItem> result = new ArrayList<CatItem>();
		CatItem parent = null;
		if (parentObj instanceof Long) {
			Long id = (Long) parentObj;
			parent = getCatById(id);
		} else if (parentObj instanceof String) {
			String parentKey = (String) parentObj;
			parent = getCatByName(key + "-" + parentKey);
		} else if (parentObj instanceof CatItem) {
			parent = (CatItem) parentObj;
		}
		List<CatItem> items = getCatsByCategory(key, language);
		Long parentId = parent.getId();
		for (CatItem item : items)
			if (item.getParentKey().equals(parentId))
				result.add(item);
		return result;

	}

	public List<CatItem> getRegionsList(String language) {
		return getCatsByCategory("Country.Regions", language);
	}

	public List<String> getRegionsString(String language) {
		List<CatItem> items = getCatsByCategory("Country.Regions", language);
		List<String> result = new ArrayList<String>();
		for (CatItem item : items)
			result.add(item.getValue());
		return result;
	}

	public CatItem getCatByValue(String catValue, String language) {
		final String name = catValue;
		Optional<Entry<String, CatItem>> item = catByName.entrySet().stream()
				.filter(entry -> entry.getValue().getValue().equalsIgnoreCase(name)).findFirst();
		return item.get().getValue();
	}

	public List<CatItem> getSettlmentsList(Object parentObj, String language) {
		List<CatItem> result = new ArrayList<CatItem>();
		CatItem region = null;
		if (parentObj instanceof Long) {
			Long id = (Long) parentObj;
			region = getCatById(id);
		} else if (parentObj instanceof String) {
			String key = (String) parentObj;
			region = getCatByName("Country.Regions" + "-" + key);
		} else if (parentObj instanceof String) {
			region = (CatItem) parentObj;
		}
		List<CatItem> settlments = getCatsByCategory("Regions.Settlments", language);
		Long parentId = region.getId();
		for (CatItem item : settlments)
			if (item.getParentKey().equals(parentId))
				result.add(item);
		return result;
	}

	public List<String> getSettlmentsStrings(Object parentObj, String language) {
		List<CatItem> items = getSettlmentsList(parentObj, language);
		List<String> result = new ArrayList<String>();
		for (CatItem item : items)
			result.add(item.getValue());
		return result;
	}

	public CatItem getCatByName(String name) {
		return catByName.get(name);
	}

	public CatItem getCatById(Long id) {
		return catById.get(id);
	}

	public List<Member> getClients() {
		return clients;
	}

	public void setClients(List<Member> clients) {
		this.clients = clients;
	}

	public List<Proposal> getProposals() {
		return proposals;
	}

	public List<Proposal> getActions() {
		return actions;
	}

	public void setProposals(List<Proposal> proposals) {
		this.proposals = proposals;
	}

	private CatItem addToParent(CatItem parent, String itemKey, String itemValue, String itemLanguage) {
		CatItem item = new CatItem(id++, itemKey, itemLanguage, itemValue, 1000, "");
		catByName.put(itemKey + "-" + itemValue, item);
		catById.put(item.getId(), item);
		return item;
	}

	private CatItem addToParentwithImage(CatItem parent, String itemKey, String itemValue, String itemLanguage,
			String image) {
		CatItem item = addToParent(parent, itemKey, itemValue, itemLanguage);
		item.setAddValue(image);
		return item;
	}

	public List<Member> getClubMembers() {
		return clients.stream().filter(mbr -> mbr.getUserType() == UserType.MEMBER).collect(Collectors.toList());
	}

	public List<Member> getClubStaff() {
		return clients
				.stream().filter(mbr -> (mbr.getUserType() == UserType.SUPERVISOR
						|| mbr.getUserType() == UserType.MODERATOR || mbr.getUserType() == UserType.STACKHOLDER))
				.collect(Collectors.toList());
	}

	public BusinessPartner getBusinessPartnerById(long id) {
		Optional<BusinessPartner> p = partners.stream().filter(entry -> entry.getId() == id).findFirst();
		return p.isPresent() ? p.get() : null;
	}

	public Member getPartnerStaffById(long id) {
		Optional<Member> member = clients.stream()
				.filter(mbr -> (mbr.getUserType() == UserType.PARTNER && mbr.getId() == id)).findFirst();
		return member.isPresent() ? member.get() : null;
	}

	public Person getUser(String login) {
		return clients.stream().filter(mbr -> mbr.getLogin().equals(login)).findAny().orElse(null);
	}

	private Member createMember(Integer id, String name, String lastName, Gender gender, UserType user, String login,
			String... others) {
		Member p = new Member();
		p.setId((long) ++id);
		p.setFirstName(name);
		p.setLastName(lastName);
		p.setGender(gender.ordinal());
		p.setUserType(user);
		p.setLogin(login);
		p.setStatus(ClientStatus.ACTIVE);
		return p;
	}

	/*
	 * public void createRegions() { List<CatItem> result = new
	 * ArrayList<CatItem>(); result.add(new CatItem(id++, "Country.Regions", "RU",
	 * "Северный", 1000, (long) 0)); result.add(new CatItem(id++, "Country.Regions",
	 * "RU", "Южный", 1000, (long) 0)); haifaRegion = new CatItem(id++,
	 * "Country.Regions", "RU", "Хайфа", 1000, (long) 0); result.add(haifaRegion);
	 * result.add(new CatItem(id++, "Country.Regions", "RU", "Центральный", 1000,
	 * (long) 0)); result.add(new CatItem(id++, "Country.Regions", "RU",
	 * "Иудея и Самария", 1000, (long) 0)); result.add(new CatItem(id++,
	 * "Country.Regions", "RU", "Тель-Авив", 1000, (long) 0)); result.add(new
	 * CatItem(id++, "Country.Regions", "RU", "Иерусалим", 1000, (long) 0));
	 * result.add(new CatItem(id++, "Country.Regions", "EN", "North", 1000, (long)
	 * 0)); result.add(new CatItem(id++, "Country.Regions", "EN", "South", 1000,
	 * (long) 0)); result.add(new CatItem(id++, "Country.Regions", "EN", "Haifa",
	 * 1000, (long) 0)); result.add(new CatItem(id++, "Country.Regions", "EN",
	 * "Center", 1000, (long) 0)); result.add(new CatItem(id++, "Country.Regions",
	 * "EN", "Judea and Samaria", 1000, (long) 0)); result.add(new CatItem(id++,
	 * "Country.Regions", "EN", "Tel-Aviv", 1000, (long) 0)); result.add(new
	 * CatItem(id++, "Country.Regions", "EN", "Jerusalem", 1000, (long) 0)); for
	 * (CatItem item : result) { catByName.put("Country.Regions" + "-" +
	 * item.getValue(), item); catById.put(item.getId(), item); } }
	 * 
	 * private void createMeasures() { measures.add(new CatItem(id++, "Measures",
	 * "RU", "кг", 1000, (long) 0)); measures.add(new CatItem(id++, "Measures",
	 * "RU", "шт", 1000, (long) 0)); measures.add(new CatItem(id++, "Measures",
	 * "RU", "л", 1000, (long) 0)); measures.add(new CatItem(id++, "Measures", "EN",
	 * "kg", 1000, (long) 0)); measures.add(new CatItem(id++, "Measures", "EN",
	 * "piece", 1000, (long) 0)); measures.add(new CatItem(id++, "Measures", "EN",
	 * "liter", 1000, (long) 0));
	 * 
	 * for (CatItem item : measures) { catByName.put("Measures" + "-" +
	 * item.getValue(), item); catById.put(item.getId(), item); }
	 * 
	 * }
	 */
	private Address createAddress(String region, String street, String sity) {
		Address a = new Address();
		a.setRegion(region);
		a.setStreetAddress(street);
		a.setSettlement(sity);
		return a;
	}

	public void createSettlments() {
		CatItem item = getCatByName("Country.Regions-Северный");
		addToParent(item, "Regions.Settlments", "Кармиэль", "RU");
		addToParent(getCatByName("Country.Regions-Северный"), "Regions.Settlments", "Кфар Манда", "RU");
		addToParent(getCatByName("Country.Regions-Северный"), "Regions.Settlments", "Нацрат Элит", "RU");

		haifa = addToParent(getCatByName("Country.Regions-Хайфа"), "Regions.Settlments", "Хайфа", "RU");
		addToParent(getCatByName("Country.Regions-Хайфа"), "Regions.Settlments", "Нешер", "RU");
		addToParent(getCatByName("Country.Regions-Хайфа"), "Regions.Settlments", "Крайот", "RU");

		addToParent(getCatByName("Country.Regions-Южный"), "Regions.Settlments", "Беэр-Шева", "RU");
		addToParent(getCatByName("Country.Regions-Южный"), "Regions.Settlments", "Ришон-ле-Цион", "RU");

		addToParent(getCatByName("Country.Regions-Центральный"), "Regions.Settlments", "Бат Ям", "RU");
		addToParent(getCatByName("Country.Regions-Центральный"), "Regions.Settlments", "Герцлия", "RU");
		addToParent(getCatByName("Country.Regions-Центральный"), "Regions.Settlments", "Кфар саба", "RU");
		addToParent(getCatByName("Country.Regions-Центральный"), "Regions.Settlments", "Рамла", "RU");

		addToParent(getCatByName("Country.Regions-Иудея и Самария"), "Regions.Settlments", "Ариэль", "RU");

		addToParent(getCatByName("Country.Regions-Иерусалим"), "Regions.Settlments", "Иерусалим", "RU");
		addToParent(getCatByName("Country.Regions-Иерусалим"), "Regions.Settlments", "Бэйт Шемеш", "RU");

		addToParent(getCatByName("Country.Regions-Тель-Авив"), "Regions.Settlments", "Тель-Авив Савидор", "RU");
		addToParent(getCatByName("Country.Regions-Тель-Авив"), "Regions.Settlments", "Питах Тиква", "RU");
		addToParent(getCatByName("Country.Regions-Тель-Авив"), "Regions.Settlments", "Яффо", "RU");

		addToParent(getCatByName("Country.Regions-North"), "Regions.Settlments", "Karmiel", "EN");
		addToParent(getCatByName("Country.Regions-North"), "Regions.Settlments", "Kfar Manda", "EN");
		addToParent(getCatByName("Country.Regions-North"), "Regions.Settlments", "Natsrat Elit", "EN");

		addToParent(getCatByName("Country.Regions-Haifa"), "Regions.Settlments", "Haifa", "EN");
		addToParent(getCatByName("Country.Regions-Haifa"), "Regions.Settlments", "Nesher", "EN");
		addToParent(getCatByName("Country.Regions-Haifa"), "Regions.Settlments", "Krayot", "EN");

		addToParent(getCatByName("Country.Regions-South"), "Regions.Settlments", "Beer Sheva", "EN");
		addToParent(getCatByName("Country.Regions-South"), "Regions.Settlments", "Rishon-le-Zion", "EN");

		addToParent(getCatByName("Country.Regions-Center"), "Regions.Settlments", "Bat yam", "EN");
		addToParent(getCatByName("Country.Regions-Center"), "Regions.Settlments", "Gertslia", "EN");
		addToParent(getCatByName("Country.Regions-Center"), "Regions.Settlments", "Kfar saba", "EN");
		addToParent(getCatByName("Country.Regions-Center"), "Regions.Settlments", "Ramla", "EN");

		addToParent(getCatByName("Country.Regions-Judea and Samaria"), "Regions.Settlments", "Ariel", "EN");

		addToParent(getCatByName("Country.Regions-Jerusalem"), "Regions.Settlments", "Jerusalem", "EN");
		addToParent(getCatByName("Country.Regions-Jerusalem"), "Regions.Settlments", "Beit shemesh", "EN");

		addToParent(getCatByName("Country.Regions-Tel-Aviv"), "Regions.Settlments", "Tel-Aviv savidor", "EN");
		addToParent(getCatByName("Country.Regions-Tel-Aviv"), "Regions.Settlments", "Petah tikva", "EN");
		addToParent(getCatByName("Country.Regions-Tel-Aviv"), "Regions.Settlments", "Jaffo", "EN");
	}

	/*
	 * private void createGoodsCategory() { //long id, String key, String language,
	 * Long parentId, String value, Integer sortOrder) CatItem item = new
	 * CatItem(id++, "Goods.Category", "RU", "Продукты питания", 1000, (long) 0);
	 * catByName.put("Goods.Category" + "-" + item.getValue(), item);
	 * addToParentwithImage(item, "Goods.Category", "Мясные продукты",
	 * "RU","salami.png"); addToParentwithImage(item, "Goods.Category",
	 * "Овощи и фрукты", "RU","fruits.png"); addToParentwithImage(item,
	 * "Goods.Category", "Деликатесы", "RU","seafoods.png");
	 * addToParentwithImage(item, "Goods.Category", "Алкоголь", "RU","wine.png");
	 * addToParentwithImage(item, "Goods.Category", "Сладости", "RU","sw.png");
	 * addToParentwithImage(item, "Goods.Category", "Сыр", "RU","cheese.png");
	 * 
	 * item = new CatItem(id++, "Goods.Category", "RU", "Электроника", 1000, (long)
	 * 0); catByName.put("Goods.Category" + "-" + item.getValue(), item);
	 * addToParent(item, "Goods.Category", "Музыка и звук", "RU"); addToParent(item,
	 * "Goods.Category", "Телевидение и компьютеры", "RU"); addToParent(item,
	 * "Goods.Category", "Мобильные устройства", "RU");
	 * 
	 * item = new CatItem(id++, "Goods.Category", "RU", "Товары для дома", 1000,
	 * (long) 0); catByName.put("Goods.Category" + "-" + item.getValue(), item);
	 * addToParent(item, "Goods.Category", "Кухни", "RU"); addToParent(item,
	 * "Goods.Category", "Спальни", "RU"); addToParent(item, "Goods.Category",
	 * "Службы", "RU"); // ********************************************************
	 * item = new CatItem(id++, "Goods.Category", "EN", "Food", 1000, (long) 0);
	 * catByName.put("Goods.Category" + "-" + item.getValue(), item);
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
	 * 
	 * }
	 */

	public List<Member> createStaff() {
		Store address = new Store(id++, "Кулаки и дули", (long) 0);
		club.setId(969);
		club.setName("Клуб 'Коним Хахамим");
		List<Contact> contacts = new ArrayList<>();
		Member employee = createMember(1, "Борис", "Шустер", Gender.MALE, UserType.SUPERVISOR, "boris", "123");
		Contact c = new Contact(employee.getFirstName(), employee.getLastName(), employee.getPhone());
		employee.setPartnerId((long) 969);
		contacts.add(c);
		clients.add(employee);
		employee = createMember(2, "Инна", "Шустер", Gender.FEMALE, UserType.SUPERVISOR, "inna", "123");
		employee.setPartnerId((long) 969);
		c = new Contact(employee.getFirstName(), employee.getLastName(), employee.getPhone());
		contacts.add(c);
		clients.add(employee);
		employee = createMember(3, "Владимир", "Олевский", Gender.MALE, UserType.MODERATOR, "vlad", "123");
		c = new Contact(employee.getFirstName(), employee.getLastName(), employee.getPhone());
		employee.setPartnerId((long) 969);
		contacts.add(c);
		clients.add(employee);
		employee = createMember(4, "Хаим", "Шапошник", Gender.MALE, UserType.STACKHOLDER, "haim", "123");
		c = new Contact(employee.getFirstName(), employee.getLastName(), employee.getPhone());
		employee.setPartnerId((long) 969);
		contacts.add(c);
		clients.add(employee);
		club.setContacts(contacts);
		club.setPhone("+972559195963");
		club.setFullName(club.getName());
		return clients;
	}

	public void createMembers() {
		Member member = createMember(2000, "Арон", "Беседер", Gender.MALE, UserType.PARTNER, "aron", "123");
		member.setPartnerId((long) 11111);
		member.setPhone("+972557776543");
		clients.add(member);
		member = createMember(2001, "Мэй", "Ле-гун", Gender.FEMALE, UserType.MEMBER, "may", "123");
		member.setLevel(MemberStatus.SIMPLE.ordinal());
		member.setPhone("0537183654");
		clients.add(member);
		member = createMember(2002, "Зион", "Розенблюм", Gender.MALE, UserType.MEMBER, "zion", "123");
		member.setLevel(MemberStatus.GOLD.ordinal());
		clients.add(member);
		member = createMember(2003, "Лена", "Коган", Gender.FEMALE, UserType.MEMBER, "lena", "123");
		member.setLevel(MemberStatus.SIMPLE.ordinal());
		clients.add(member);
		member = createMember(2004, "Света", "Левинская", Gender.FEMALE, UserType.MEMBER, "Svetlana", "123");
		member.setLevel(MemberStatus.PLATINUM.ordinal());
		member.setLevel(MemberStatus.GOLD.ordinal());
		clients.add(member);
		member = createMember(2005, "Моше", "Цибулински", Gender.MALE, UserType.PARTNER, "moshe", "123");
		member.setLevel(MemberStatus.GOLD.ordinal());
		clients.add(member);
	}

	private Member getRandomMember() {
		int index = random.nextInt(clients.size() - 1);
		return clients.get(index);
	}

	private List<CatItem> getRandomCategories(CatItem parent) {
		List<CatItem> result = new ArrayList<>();
		result.add(parent);
		List<CatItem> items = getChildCatsByCategory(parent.getKey(), parent, parent.getLanguage());
		CatItem rndSubCat = items.get(items.size() - 1);
		result.add(rndSubCat);
		return result;
	}

	public Proposal createProposal(Long id, String name, List<CatItem> categories, CatItem region, Member author,
			Float price, LocalDate dueDate) {
		Proposal pp = new Proposal();
		pp.setId(id);
		pp.setName(name);
		if (categories != null && categories.size() > 0)
			pp.setCategory(categories.get(0).getValue());
		pp.setRegion(region.getValue());
		pp.setInitiator(author);
		pp.setPrice(price);
		pp.setDueDate(dueDate);
		return pp;
	}

	public void createProposals() {
		CatItem haifaReg = getCatByValue("Хайфа", "RU");
		CatItem telavivReg = getCatByValue("Тель-Авив", "RU");
		CatItem foodCat = getCatByValue("Продукты питания", "RU");
		Map<String, String[]> productMap = new HashMap<>();
		String[] ajectives = "свежая,недорогая,эксклюзивная,традиционная,деликатесная,особая, вкусная".split(",");
		productMap.put("Деликатесы", "Икра,Лососина,Семга,Селедка,Кета".split(","));
		productMap.put("Мясные продукты", "Свинина,Колбаса,Рыба,Говядина,Баранина".split(","));
		productMap.put("Овощи и фрукты", "Вишня,Черешня,Клубника,Дыня,Слива,Груша,Оливка".split(","));
		productMap.put("Алкоголь", "Водка,Текила,Малага,Настойка".split(","));

		for (int i = 0; i < 20; i++) {
			LocalDate dueDate = LocalDate.now();
			dueDate.plusDays(random.nextInt(14));
			Member author = getRandomMember();
			Float randomPrice = (float) random.nextInt(100);
			List<CatItem> cats = getRandomCategories(foodCat);
			String[] lst = productMap.get(cats.get(1).getValue());
			String name = lst[random.nextInt(lst.length - 1)] + " " + ajectives[random.nextInt(ajectives.length - 1)];

			Proposal p = null;
			if (i % 2 == 0)
				p = createProposal(id++, name, cats, haifaReg, author, randomPrice, dueDate);
			else
				p = createProposal(id++, name, cats, telavivReg, author, randomPrice, dueDate);
			p.setMaxPrice(random.nextFloat());
			p.setStatus("ProposalStatus.PUBLISHED");
			p.setSupplier("Сеть магазинов 'Мама'");
			p.setSupplierId((long) 999);
			p.setMeasure("кг");
			proposals.add(p);
		}
	}

	public PriceProposal createPriceProposal(Long proposalId, Float price, Float quantity, Integer level) {
		PriceProposal pp = new PriceProposal();
		pp.setProposalId(proposalId);
		pp.setPriceLevel(level);
		pp.setPrice(price);
		pp.setQuantity(quantity);
		return pp;
	}

	public List<CatItem> getCategories() {
		return categories;
	}

	public void setCategories(List<CatItem> categories) {
		this.categories = categories;
	}

	public Proposal getAction(long id) {
		Optional<Proposal> found = actions.stream().filter(action -> action.getId() == id).findFirst();
		return found.isPresent() ? found.get() : null;
	}

	public Long addAction(Proposal p) {
		Proposal last = actions.get(actions.size() - 1);
		Long id = last.getId() + 1;
		p.setId(id);
		actions.add(p);
		return id;
	}

	public boolean updateAction(Proposal p) {
		try {
			int ind = 0;
			for (Proposal action : actions) {
				if (action.getId() == id) {
					actions.set(ind, p);
					return true;
				}
				ind++;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void createActions() {
		ArrayList<Address> stores = new ArrayList<Address>();
		stores.add(createAddress("Тель-Авив", "ул. Герцль 60", "Бат-Ям"));
		stores.add(createAddress("Тель-Авив", "ул. Жаботински 133", "Рамат-Ган"));
		CatItem telavivReg = getCatByValue("Тель-Авив", "RU");

		CatItem[] foodcat = new CatItem[6];
		foodcat[0] = getCatByValue("Мясные продукты", "RU");
		foodcat[1] = getCatByValue("Сыр", "RU");
		foodcat[2] = getCatByValue("Алкоголь", "RU");
		foodcat[3] = getCatByValue("Овощи и фрукты", "RU");
		foodcat[4] = getCatByValue("Деликатесы", "RU");
		foodcat[5] = getCatByValue("Сладости", "RU");

		String[] names = new String[6];
		names[0] = "колбаса вареная";
		names[1] = "сыр моцарелла";
		names[2] = "пиво Гиннес";
		names[3] = "бананы";
		names[4] = "икра красная";
		names[5] = "шоколад ";

		String[] desc = new String[6];
		desc[0] = "индюшиная со специями";
		desc[1] = "производство Италия";
		desc[2] = "банка 500 мл";
		desc[3] = "минимальный";
		desc[4] = "банка вес 150  гр";
		desc[5] = "молочный с орехами плитка 100гр";
		Float[] prices = new Float[6];
		prices[0] = (float) 60.0;
		prices[1] = (float) 80.0;
		prices[2] = (float) 7.0;
		prices[3] = (float) 25.0;
		prices[4] = (float) 300.0;
		prices[5] = (float) 90.0;

		for (int i = 0; i < names.length; i++) {
			LocalDate dueDate = LocalDate.now();
			dueDate.plusDays(random.nextInt(14));
			Member author = getRandomMember();
			String name = names[i];
			Long id = (long) (100 + i);
			ArrayList<PriceProposal> variants = new ArrayList<PriceProposal>();
			variants.add(createPriceProposal(id, (float) (prices[i] * 0.6), new Float(10), 2));
			variants.add(createPriceProposal(id, (float) (prices[i] * 0.7), new Float(5), 1));
			variants.add(createPriceProposal(id, (float) (prices[i] * 0.8), new Float(3), 0));
			PriceProposal.sort(variants);
			ArrayList<CatItem> cats = new ArrayList<CatItem>();
			cats.add(getCatByValue("Продукты питания", "RU"));
			cats.add(foodcat[i]);
			Proposal ac = createProposal(id, name, cats, telavivReg, author, prices[i], dueDate);
			ac.setStatus("ProposalStatus.PUBLISHED");
			// ac.setLastPrice((float) (prices[i]*0.6));
			ac.setLastPrice((float) 0);
			ac.setPriceProposals(variants);
			ac.setStores(stores);
			ac.setThreshold((float) 0.5);
			if (i == 3) {
				ac.setMeasure("шт");
			} else {
				ac.setMeasure("кг");
			}
			if (i % 2 == 0) {
				ac.setSupplier("Бердычевские пончики");
				ac.setSupplierId((long) 998);
			} else {
				ac.setSupplier("Мааданей Росман");
				ac.setSupplierId((long) 11111);
			}

			List<String> ps = new ArrayList<String>();
			ps.add(foodcat[i].getAddValue());
			ac.setPhotos(ps);
			ac.setDescription(desc[i]);
			if (i == 4)
				ac.setThresholdmax((float) 11.0);
			calculateProposalSummary(ac);
			actions.add(ac);
		}

	}

	private void createPartner() {
		partner = new BusinessPartner();
		partner.setId((long) 11111);
		partner.setName("Мааданей Росман");
		partner.setFullName("Сеть Мааданей Росман");
		ArrayList<Store> stores = new ArrayList<Store>();
		stores.add(new Store(id++, "", partner.getId()));
		stores.add(new Store(id++, "", partner.getId()));
		partner.setStores(stores);
		ArrayList<Contact> allContacts = new ArrayList<Contact>();
		allContacts.add(new Contact("Михаил", "Коэн", "050-9999-88-77"));
		allContacts.add(new Contact("Давид", "Левин", "050-9999-88-77"));
		allContacts.add(new Contact("Арон", "Беседер", "050-9999-77-77"));
		partner.setContacts(allContacts);
		partners.add(partner);
	}

	public List<CatItem> getAllCategories(String language) {
		if (language == null)
			language = ApplicationSettings.getDefaultLanguage();
		final String lang = language;
		List<CatItem> items = new ArrayList<CatItem>();
		catByName.forEach((k, value) -> {
			if (lang.equalsIgnoreCase(value.getLanguage()))
				items.add(value);
		});
		return items;

	}

	private PriceProposal getPriceProposalById(long id) {
		return intents.stream().filter(intent -> intent.getId() == id).findFirst().orElse(null);
	}

	// считает количество во всех заказах по заявке
	// определяет тек.цену, объем закупки - по количеству и деньгам
	public Proposal calculateProposalSummary(Proposal proposal) {
		List<PriceProposal> props = proposal.getPriceProposals();
		// props.sort((a,b)->a.getPrice().compareTo(b.getPrice()));
		Float qty = (float) 0;
		// идем от верхней цены
		for (int i = props.size() - 1; i >= 0; i--) {
			PriceProposal pp = props.get(i);
			qty = getMembersPriceIntentsByLevel(proposal.getId(), pp.getPriceLevel());
			if (qty >= pp.getQuantity()) { // этот уровень достигнут
				// запоминаем значение текущей цены, как клубной, конец перебора
				proposal.setLastPrice(pp.getPrice());
				proposal.setTotal(qty);
				break;
			}
			proposal.setLastPrice((float) 0);
			proposal.setTotal(qty);
		}
		return proposal;
	}

	public Float getMembersPriceIntentsByLevel(Long proposalId, Integer level) {
		Float sum = (float) 0;
		sum = intents.stream()
				.filter(intent -> intent.getProposalId().equals(proposalId) && intent.getPriceLevel() == level
						&& intent.getProposalType() == 1)
				.map(prop -> prop.getQuantity()).reduce((float) 0, (a, b) -> a + b);
		return sum;
	}

	public List<PriceProposal> getMembersPriceIntents(Long proposalId, Long memberId) {
		List<PriceProposal> res = intents.stream()
				.filter(intent -> intent.getProposalId().equals(proposalId) && intent.getMemberId().equals(memberId))
				.collect(Collectors.toList());
		if (res.isEmpty())
			return new ArrayList<PriceProposal>();
		res.sort(Collections.reverseOrder((a, b) -> a.getPrice().compareTo(b.getPrice())));
		return res;
	}

	public List<PriceProposal> getAllMembersPriceIntents(Long memberId) {
		List<PriceProposal> res = intents.stream().filter(intent -> intent.getMemberId().equals(memberId))
				.collect(Collectors.toList());
		if (res.isEmpty())
			return new ArrayList<PriceProposal>();
		res.sort(Collections.reverseOrder((a, b) -> a.getPrice().compareTo(b.getPrice())));
		return res;
	}

	public List<Proposal> getAllMemberActions(Long memberId) {
		List<PriceProposal> myIntents = getAllMembersPriceIntents(memberId);
		List<Proposal> result = new ArrayList<>();
		final List<Long> myActionsId = new ArrayList<>();
		for (PriceProposal pp : myIntents)
			if (pp.getMemberId().equals(memberId) && !myActionsId.contains(pp.getProposalId()))
				myActionsId.add(pp.getProposalId());
		result = actions.stream().filter(action -> myActionsId.contains(action.getId())).collect(Collectors.toList());
		return result;
	}

	public void saveMemberPriceIntent(PriceProposal intent) {
		if (intent.getId() == 0) {
			id++;
			intent.setId(id);
			intents.add(intent);
		} else {
			PriceProposal found = getPriceProposalById(intent.getId());
			if (found != null) {
				intents.remove(found);
				intents.add(intent);
			} else {
				intents.add(intent);
			}
		}
	}

	public String saveMemberPriceIntents(List<PriceProposal> intents) {
		for (PriceProposal intent : intents)
			saveMemberPriceIntent(intent);
		PriceProposal.sort(intents);
		Proposal p = getAction(intents.get(0).getProposalId());
		p = calculateProposalSummary(p);
		updateAction(p);
		return "";
	}

	public void createIntents() {
		// Борис заказал
		PriceProposal pp = new PriceProposal();
		pp.setMemberId((long) 2);
		pp.setPriceLevel(0);
		pp.setPrice((float) 240);
		pp.setProposalId((long) 104);
		pp.setProposalType(1);
		pp.setQuantity((float) 1);
		saveMemberPriceIntent(pp);

		pp = new PriceProposal();
		pp.setMemberId((long) 2);
		pp.setPrice((float) 210);
		pp.setPriceLevel(1);
		pp.setProposalId((long) 104);
		pp.setProposalType(1);
		pp.setQuantity((float) 4);
		saveMemberPriceIntent(pp);

		pp = new PriceProposal();
		pp.setMemberId((long) 2);
		pp.setPrice((float) 180);
		pp.setPriceLevel(2);
		pp.setProposalId((long) 104);
		pp.setProposalType(1);
		pp.setQuantity((float) 8);
		saveMemberPriceIntent(pp);

		// Терпила
		pp = new PriceProposal();
		pp.setMemberId((long) 2002);
		pp.setPriceLevel(0);
		pp.setPrice((float) 240);
		pp.setProposalId((long) 104);
		pp.setProposalType(1);
		pp.setQuantity((float) 1);
		saveMemberPriceIntent(pp);

		pp = new PriceProposal();
		pp.setMemberId((long) 2002);
		pp.setPrice((float) 210);
		pp.setPriceLevel(1);
		pp.setProposalId((long) 104);
		pp.setProposalType(1);
		pp.setQuantity((float) 1);
		saveMemberPriceIntent(pp);

		pp = new PriceProposal();
		pp.setMemberId((long) 2002);
		pp.setPrice((float) 180);
		pp.setPriceLevel(2);
		pp.setProposalId((long) 104);
		pp.setProposalType(1);
		pp.setQuantity((float) 3);
		saveMemberPriceIntent(pp);

		Proposal a = getAction(104);
		calculateProposalSummary(a);

		// ************************************
		pp = new PriceProposal();
		pp.setMemberId((long) 2001);
		pp.setPriceLevel(0);
		pp.setPrice((float) 48);
		pp.setProposalId((long) 100);
		pp.setProposalType(1);
		pp.setQuantity((float) 2);
		saveMemberPriceIntent(pp);

		pp = new PriceProposal();
		pp.setMemberId((long) 2001);
		pp.setPrice((float) 42);
		pp.setPriceLevel(1);
		pp.setProposalId((long) 100);
		pp.setProposalType(1);
		pp.setQuantity((float) 2);
		saveMemberPriceIntent(pp);

		pp = new PriceProposal();
		pp.setMemberId((long) 2001);
		pp.setPrice((float) 36);
		pp.setPriceLevel(2);
		pp.setProposalId((long) 100);
		pp.setProposalType(1);
		pp.setQuantity((float) 3);
		saveMemberPriceIntent(pp);

		a = getAction(100);
		calculateProposalSummary(a);
		// ********************************************

		pp = new PriceProposal();
		pp.setMemberId((long) 20000);
		pp.setPriceLevel(0);
		pp.setPrice((float) 64);
		pp.setProposalId((long) 101);
		pp.setProposalType(1);
		pp.setQuantity((float) 10);
		saveMemberPriceIntent(pp);

		pp = new PriceProposal();
		pp.setMemberId((long) 20001);
		pp.setPrice((float) 56);
		pp.setPriceLevel(1);
		pp.setProposalId((long) 101);
		pp.setProposalType(1);
		pp.setQuantity((float) 12);
		saveMemberPriceIntent(pp);

		pp = new PriceProposal();
		pp.setMemberId((long) 20001);
		pp.setPrice((float) 48);
		pp.setPriceLevel(2);
		pp.setProposalId((long) 101);
		pp.setProposalType(1);
		pp.setQuantity((float) 13);
		saveMemberPriceIntent(pp);

		a = getAction(101);
		calculateProposalSummary(a);
		// ********************************************

		pp = new PriceProposal();
		pp.setMemberId((long) 20000);
		pp.setPriceLevel(0);
		pp.setPrice((float) 5.6);
		pp.setProposalId((long) 102);
		pp.setProposalType(1);
		pp.setQuantity((float) 5);
		saveMemberPriceIntent(pp);

		pp = new PriceProposal();
		pp.setMemberId((long) 20001);
		pp.setPrice((float) 4.9);
		pp.setPriceLevel(1);
		pp.setProposalId((long) 102);
		pp.setProposalType(1);
		pp.setQuantity((float) 5);
		saveMemberPriceIntent(pp);

		pp = new PriceProposal();
		pp.setMemberId((long) 20001);
		pp.setPrice((float) 4.2);
		pp.setPriceLevel(2);
		pp.setProposalId((long) 102);
		pp.setProposalType(1);
		pp.setQuantity((float) 5);
		saveMemberPriceIntent(pp);

		a = getAction(102);
		calculateProposalSummary(a);
		// ********************************************

	}

	public MockService() {

		// createGoodsCategory();

		// createRegions();
		// createMeasures();
		createSettlments();
		createStaff();
		createMembers();

		// createProposals();
		createActions();
		createPartner();
		createIntents();

	}

}
