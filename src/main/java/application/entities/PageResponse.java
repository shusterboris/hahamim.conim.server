package application.entities;

import java.util.List;

public class PageResponse<T> {
	List<T> entities;
	Integer totalPages = 0;
	Long totalRecords = (long) 0;

	public PageResponse(List<T> entities, Integer totalPages, Long recs) {
		super();
		this.entities = entities;
		this.totalPages = totalPages;
		this.totalRecords = recs;
	}

	public List<T> getEntities() {
		return entities;
	}

	public void setEntities(List<T> entities) {
		this.entities = entities;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

}
