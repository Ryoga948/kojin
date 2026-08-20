package jp.co.ctct.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jp.co.ctct.model.Book;
import jp.co.ctct.model.Category;
import jp.co.ctct.repository.BookRepository;
import jp.co.ctct.repository.CategoryRepository;

@Service
public class BookService {

	private static final int BOOKS_PER_PAGE = 5;

	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;

	public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
		this.bookRepository = bookRepository;
		this.categoryRepository = categoryRepository;
	}

	public Page<Book> searchBooks(Long categoryId, String isbn, String name, String author,
			String publisher, int page) {
		Pageable pageable = PageRequest.of(page, BOOKS_PER_PAGE);
		return bookRepository.search(categoryId, isbn, name, author, publisher, pageable);
	}

	public Book findBook(Long id) {
		return bookRepository.findById(id).orElseThrow();
	}

	public List<Category> findAllCategories() {
		return categoryRepository.findAll();
	}

	public void addBook(Book book) {
		bookRepository.save(book);
	}

	public void updateBook(Book editedBook) {
		Book currentBook = findBook(editedBook.getId());
		currentBook.setCategory(editedBook.getCategory());
		currentBook.setIsbn(editedBook.getIsbn());
		currentBook.setName(editedBook.getName());
		currentBook.setAuthor(editedBook.getAuthor());
		currentBook.setPublisher(editedBook.getPublisher());
		currentBook.setPublishedAt(editedBook.getPublishedAt());
		bookRepository.save(currentBook);
	}
}
