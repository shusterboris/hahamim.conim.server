package application.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import enums.ProposalStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "purchase")
public class Purchase extends BasicEntity implements Serializable {

	private static final long serialVersionUID = 683915625637469600L;
	private String name;
	private ProposalStatus state;
	private Long initiator;

	private LocalDate currDate; // дата инициации либо дата выкупа в зависимости от состояния
}