package jp.co.ctct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.ctct.model.Lend;

public interface LendRepository extends JpaRepository<Lend, Long> {
}
