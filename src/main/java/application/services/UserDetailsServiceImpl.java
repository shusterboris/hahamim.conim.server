package application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import application.entities.security.User;
import application.services.repositories.UserDAO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserDAO userDAO;

	@Autowired
	PasswordEncoder userPasswordEncoder;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException(username);
		return user;
	}

	public String saveUser(User user) {
		try {
			if (user.getPassword() != null || !user.getPassword().startsWith("$"))
				user.setPassword(userPasswordEncoder.encode(user.getPassword()));
			userDAO.save(user);
			return "";
		} catch (Exception e) {
			return "Can't save user";
		}
	}

	public boolean userExists(String login) {
		return userDAO.findByUsername(login) != null;
	}
}
