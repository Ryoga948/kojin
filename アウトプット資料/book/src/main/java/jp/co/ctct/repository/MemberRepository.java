package jp.co.ctct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.ctct.model.Member;

public interface MemberRepository extends JpaRepository<Member,Long>{
}
