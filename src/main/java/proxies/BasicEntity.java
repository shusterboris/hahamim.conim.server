package proxies;

import java.io.Serializable;

public abstract class BasicEntity implements Serializable{
	private static final long serialVersionUID = -1247459189884826799L;
	protected long id;
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	



}
