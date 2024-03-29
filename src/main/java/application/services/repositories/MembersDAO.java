package application.services.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import application.entities.Member;

@Repository
public interface MembersDAO extends PagingAndSortingRepository<Member, Long>, QueryByExampleExecutor<Member> {

	public Optional<Member> findById(Long id);

	public Optional<Member> findByLogin(String login);

	public Optional<Member> findByPhone(String phone);

	public Page<Member> findByPhoneContaining(String phone, Pageable p);

	public Optional<Member> findByEmail(String email);

	@Query("SELECT m FROM Member m WHERE m.firstName like ?1 or m.lastName like ?1 or m.email like ?1")
	public Page<Member> findMemberContainsValue(String s, Pageable p);

	public Page<Member> findByTelegramContaining(String id, Pageable p);



}
