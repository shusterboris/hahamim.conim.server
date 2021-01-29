package application.entities.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest implements Serializable {
	private static final long serialVersionUID = 6060069636934880974L;
	private String username;
	private String password;

	// need default constructor
	public JwtRequest() {
	}

	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}
}
