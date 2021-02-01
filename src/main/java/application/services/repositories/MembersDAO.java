package application.services.repositories;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import application.entities.Member;

@Repository
public interface MembersDAO extends PagingAndSortingRepository<Member, Long>, QueryByExampleExecutor<Member> {
	public List<Member> findAll();

	public Page<Member> findAll(Pageable p);

	public Page<Member> findAll(Example<Member> memberExamle, Pageable p);

	public Optional<Member> findById(Long id);

	public Optional<Member> findByLogin(String login);

	public Optional<Member> findByPhone(String phone);

	public Optional<Member> findByEmail(String email);
}
