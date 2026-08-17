package jp.co.ctct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.ctct.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);

}
