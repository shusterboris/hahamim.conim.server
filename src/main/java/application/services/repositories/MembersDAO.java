package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import application.entities.Member;


@Repository
public interface MembersDAO extends CrudRepository<Member, Long>{
	public List<Member> findAll();
	public Optional<Member> findById(Long id);
	public Optional<Member> findByLogin(String login);
}
