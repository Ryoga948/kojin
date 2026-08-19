package jp.co.ctct.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.ctct.model.Lend;

public interface LendRepository extends JpaRepository<Lend, Long> {

	List<Lend> findAllByOrderByIdAsc();

	List<Lend> findByReturnedDueAtOrderByIdAsc(LocalDate returnedDueAt);
}
