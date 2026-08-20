package jp.co.ctct.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.ctct.model.Book;
import jp.co.ctct.model.BookDetail;
import jp.co.ctct.model.Category;
import jp.co.ctct.model.Member;
import jp.co.ctct.repository.BookDetailRepository;
import jp.co.ctct.repository.BookRepository;
import jp.co.ctct.repository.CategoryRepository;
import jp.co.ctct.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class UpdateServiceTests {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private BookDetailRepository bookDetailRepository;

	@Mock
	private MemberRepository memberRepository;

	@Test
	void updateBookCopiesEditableFields() {
		Book currentBook = new Book();
		currentBook.setId(1L);
		Book editedBook = createEditedBook();
		when(bookRepository.findById(1L)).thenReturn(Optional.of(currentBook));

		BookService bookService = new BookService(bookRepository, categoryRepository);
		bookService.updateBook(editedBook);

		assertEquals("978-4-00-000000-0", currentBook.getIsbn());
		assertEquals("Spring入門", currentBook.getName());
		assertEquals("山田太郎", currentBook.getAuthor());
		assertEquals("サンプル出版", currentBook.getPublisher());
		assertEquals(LocalDate.of(2026, 8, 20), currentBook.getPublishedAt());
		assertEquals(editedBook.getCategory(), currentBook.getCategory());
		verify(bookRepository).save(currentBook);
	}

	@Test
	void updateBookDetailKeepsBookAndLendingState() {
		Book book = new Book();
		BookDetail currentBookDetail = new BookDetail();
		currentBookDetail.setBook(book);
		currentBookDetail.setLent(true);

		BookDetail editedBookDetail = new BookDetail();
		editedBookDetail.setStartAt(LocalDate.of(2026, 1, 1));
		editedBookDetail.setDisposalAt(LocalDate.of(2026, 12, 31));
		editedBookDetail.setMemo("更新後メモ");

		BookDetailService bookDetailService = new BookDetailService(bookDetailRepository);
		bookDetailService.updateBookDetail(currentBookDetail, editedBookDetail);

		assertEquals(book, currentBookDetail.getBook());
		assertEquals(LocalDate.of(2026, 1, 1), currentBookDetail.getStartAt());
		assertEquals(LocalDate.of(2026, 12, 31), currentBookDetail.getDisposalAt());
		assertEquals("更新後メモ", currentBookDetail.getMemo());
		verify(bookDetailRepository).save(currentBookDetail);
	}

	@Test
	void updateMemberKeepsOriginalCreatedDate() {
		Member currentMember = new Member();
		currentMember.setId(1L);
		currentMember.setCreatedAt(LocalDate.of(2020, 1, 1));

		Member editedMember = new Member();
		editedMember.setId(1L);
		editedMember.setName("山田花子");
		editedMember.setEmail("hanako@example.com");
		editedMember.setAddress("東京都");
		editedMember.setBirth(LocalDate.of(1990, 1, 1));
		editedMember.setPhone("090-0000-0000");
		editedMember.setZipCode("100-0001");

		when(memberRepository.findById(1L)).thenReturn(Optional.of(currentMember));
		MemberService memberService = new MemberService(memberRepository);
		memberService.updateMember(editedMember);

		assertEquals(LocalDate.of(2020, 1, 1), currentMember.getCreatedAt());
		assertEquals("山田花子", currentMember.getName());
		assertEquals("hanako@example.com", currentMember.getEmail());
		verify(memberRepository).save(currentMember);
	}

	private Book createEditedBook() {
		Category category = new Category();
		category.setId(2L);

		Book book = new Book();
		book.setId(1L);
		book.setIsbn("978-4-00-000000-0");
		book.setName("Spring入門");
		book.setAuthor("山田太郎");
		book.setPublisher("サンプル出版");
		book.setPublishedAt(LocalDate.of(2026, 8, 20));
		book.setCategory(category);
		return book;
	}
}
