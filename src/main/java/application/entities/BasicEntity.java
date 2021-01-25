package application.entities;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BasicEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	protected long id;
	protected LocalDateTime modified;
	protected LocalDateTime created;
	@Version
	private int version = 1;
	@Column(name = "is_deleted", columnDefinition = "boolean default false")
	private boolean isDeleted = false;
	
	public String toString() {
		return String.valueOf(getId());
	}
	
	@PrePersist
	protected void onCreate() {		
		setCreated(LocalDateTime.now());
		modified = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		modified = LocalDateTime.now();
	}

	

}
