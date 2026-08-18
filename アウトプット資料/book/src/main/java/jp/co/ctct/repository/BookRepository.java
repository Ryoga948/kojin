package jp.co.ctct.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.ctct.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("""
			SELECT b FROM Book b
			WHERE (:categoryId IS NULL OR b.category.id = :categoryId)
			  AND b.isbn LIKE CONCAT('%', :isbn, '%')
			  AND b.name LIKE CONCAT('%', :name, '%')
			  AND b.author LIKE CONCAT('%', :author, '%')
			  AND b.publisher LIKE CONCAT('%', :publisher, '%')
			""")
	Page<Book> search(@Param("categoryId") Long categoryId,
			@Param("isbn") String isbn,
			@Param("name") String name,
			@Param("author") String author,
			@Param("publisher") String publisher,
			Pageable pageable);
}
