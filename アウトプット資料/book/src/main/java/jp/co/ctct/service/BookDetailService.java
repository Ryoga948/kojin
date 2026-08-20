package jp.co.ctct.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ctct.model.Book;
import jp.co.ctct.model.BookDetail;
import jp.co.ctct.repository.BookDetailRepository;

@Service
public class BookDetailService {

	private final BookDetailRepository bookDetailRepository;

	public BookDetailService(BookDetailRepository bookDetailRepository) {
		this.bookDetailRepository = bookDetailRepository;
	}

	public List<BookDetail> findByBookId(Long bookId) {
		return bookDetailRepository.findByBookIdOrderByIdAsc(bookId);
	}

	public BookDetail findBookDetail(Long id) {
		return bookDetailRepository.findById(id).orElseThrow();
	}

	public void addBookDetail(Book book, BookDetail bookDetail) {
		bookDetail.setBook(book);
		bookDetailRepository.save(bookDetail);
	}

	public void updateBookDetail(BookDetail currentBookDetail, BookDetail editedBookDetail) {
		currentBookDetail.setStartAt(editedBookDetail.getStartAt());
		currentBookDetail.setDisposalAt(editedBookDetail.getDisposalAt());
		currentBookDetail.setMemo(editedBookDetail.getMemo());
		bookDetailRepository.save(currentBookDetail);
	}
}
