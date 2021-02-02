package application.services.repositories;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import application.entities.Member;

@Repository
public interface MembersDAO extends PagingAndSortingRepository<Member, Long>, QueryByExampleExecutor<Member> {

	public Optional<Member> findById(Long id);

	public Optional<Member> findByLogin(String login);

	public Optional<Member> findByPhone(String phone);

	public Optional<Member> findByEmail(String email);
}
