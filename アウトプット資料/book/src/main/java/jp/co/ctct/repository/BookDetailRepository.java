package jp.co.ctct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.ctct.model.BookDetail;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {

	List<BookDetail> findByBookIdOrderByIdAsc(Long bookId);
}
