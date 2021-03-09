package application.services.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import application.entities.Payment;


public interface PaymentDAO extends PagingAndSortingRepository<Payment, Long> {
	public List<Payment> findByMember(Long member);

	public Page<Payment> findByPurchase(Long purchase, Pageable p);
	public Optional<Payment> findById(Long id);

	@Query("SELECT SUM(p.sum) FROM Payment p WHERE p.purchase=:pid")
	public Float fetchPaymentByPurchase(@Param("pid") Long pid);
}
