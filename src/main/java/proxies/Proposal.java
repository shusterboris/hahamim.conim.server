package proxies;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import enums.ProposalStatus;

public class Proposal extends BasicEntity implements Serializable{
	private static final long serialVersionUID = -8117381935020236594L;
	private String name;
	private List<CatItem> categories;
	private CatItem region;
	private Member initiator;
	private Float price = (float) 0.0;
	private Float lastPrice = (float) 0.0;
	private LocalDate dueDate;
	private CatItem measure;
	private Float threshold;
	private String status = ProposalStatus.INIT.getMessageKey(); // �� ������ TenderStatus
	private List<String> photos;
	private LocalDate publicationDate;
	private String winner;
	private Long winnerId;
	private Float total = (float) 0.0;
	private Integer countMembers = 0;
	private LocalDate dateOfSailStarting;
	private LocalDate closeDate;
	private String description;
    private List<PriceProposal> priceProposals;
    private List<Address> stores;
    
	public List<Address> getStores() {
		return stores;
	}

	public void setStores(List<Address> stores) {
		this.stores = stores;
	}

	public List<CatItem> getCategories() {
		return categories;
	}

	public CatItem getRegion() {
		return region;
	}

	public void setRegion(CatItem region) {
		this.region = region;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public List<PriceProposal> getProposals() {
		return priceProposals;
	}

	public void setProposals(List<PriceProposal> proposals) {
		this.priceProposals = proposals;
	}

	public void setCategories(List<CatItem> categories) {
		this.categories = categories;
	}

	public CatItem getRegions() {
		return region;
	}

	public void setRegions(CatItem regions) {
		this.region = regions;
	}

	public Person getAuthor() {
		return initiator;
	}

	public void setAuthor(Member author) {
		this.initiator = author;
	}

	public Float getMaxPrice() {
		return price;
	}

	public void setMaxPrice(Float maxPrice) {
		this.price = maxPrice;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public LocalDate getDateOfSailStarting() {
		return dateOfSailStarting;
	}

	public void setDateOfSailStarting(LocalDate dateOfSailStarting) {
		this.dateOfSailStarting = dateOfSailStarting;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Float getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Float lastPrice) {
		this.lastPrice = lastPrice;
	}

	public CatItem getMeasure() {
		return measure;
	}

	public void setMeasure(CatItem measure) {
		this.measure = measure;
	}

	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}

	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Long getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(Long winnerId) {
		this.winnerId = winnerId;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Integer getCountMembers() {
		return countMembers;
	}

	public void setCountMembers(Integer countMembers) {
		this.countMembers = countMembers;
	}

	public LocalDate getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(LocalDate closeDate) {
		this.closeDate = closeDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Person getInitiator() {
		return initiator;
	}

	public void setInitiator(Member author) {
		this.initiator = author;
	}

	public List<PriceProposal> getPriceProposals() {
		return priceProposals;
	}

	public void setPriceProposals(List<PriceProposal> priceProposals) {
		this.priceProposals = priceProposals;
	}


	public Float getThreshold() {
		return threshold;
	}

	public void setThreshold(Float threshold) {
		this.threshold = threshold;
	}

	@Override
	public String toString() {
		return name;
	}

}
