package application.entities.security;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USERS", uniqueConstraints = { @UniqueConstraint(columnNames = { "USERNAME" }) })
public class User implements UserDetails, Serializable {
	private static final long serialVersionUID = 3428483824158752544L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "ACCOUNT_EXPIRED", columnDefinition = "boolean default 1")
	private boolean accountExpired = true;

	@Column(name = "ACCOUNT_LOCKED", columnDefinition = "boolean default 1")
	private boolean accountLocked = true;

	@Column(name = "CREDENTIALS_EXPIRED")
	private boolean credentialsExpired = true;

	@Column(name = "ENABLED", columnDefinition = "boolean default 1")
	private boolean enabled = true;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USERS_AUTHORITIES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"))
	@OrderBy
	@JsonIgnore
	private Collection<Authority> authorities;

	@Override
	public Collection<Authority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public User() {
		// default constructor for Spring
	}

	public User(String userName, String password) {
		this.username = userName;
		this.password = password;
	}
}
