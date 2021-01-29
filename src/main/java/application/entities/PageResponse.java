package application.entities;

import java.util.List;

public class PageResponse<T> {
	List<T> entities;
	Integer totalPages = 0;

	public PageResponse(List<T> entities, Integer totalPages) {
		super();
		this.entities = entities;
		this.totalPages = totalPages;
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

}
