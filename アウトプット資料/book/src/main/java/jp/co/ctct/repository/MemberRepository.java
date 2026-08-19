package jp.co.ctct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.ctct.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Page<Member> findByNameContainingAndPhoneContainingAndEmailContaining(
			String name, String phone, String email, Pageable pageable);

	List<Member> findByDeletedAtIsNullOrderByIdAsc();
}
