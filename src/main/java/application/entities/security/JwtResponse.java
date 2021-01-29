package application.entities.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = 4681748712282286257L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}
}
