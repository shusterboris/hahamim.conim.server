package application.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "attachments")
public class AppImage extends BasicEntity implements Serializable{
	private static final long serialVersionUID = -7073038572958126832L;
	private String imgPath;
	
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
