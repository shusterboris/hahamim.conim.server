package application.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "attachments")
public class AppImage extends BasicEntity implements Serializable{
	private static final long serialVersionUID = -7073038572958126832L;
 	
	private String imgPath;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "proposal_id", nullable = false)
	private Proposal proposal;
	
	public AppImage(String im) {
		// TODO Auto-generated constructor stub
	}
	public String getImgPath() {
		return imgPath;
	}
	
	public AppImage() {
		
	}
	
	public AppImage(String imgPath, Proposal proposal) {
		super();
		this.imgPath = imgPath;
		this.proposal = proposal;
	}
	
}
