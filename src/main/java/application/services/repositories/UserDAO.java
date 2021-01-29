package application.services.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import application.entities.security.User;


@Repository
public interface UserDAO extends CrudRepository<User, Long> {
	public User findByUsername(String username);

}
