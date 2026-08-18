package jp.co.ctct.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.ctct.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
