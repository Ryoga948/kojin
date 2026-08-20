package jp.co.ctct.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.ctct.model.Book;
import jp.co.ctct.model.BookDetail;
import jp.co.ctct.model.Lend;
import jp.co.ctct.model.Member;
import jp.co.ctct.repository.BookDetailRepository;
import jp.co.ctct.repository.LendRepository;
import jp.co.ctct.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class LendServiceTests {

	@Mock
	private LendRepository lendRepository;

	@Mock
	private BookDetailRepository bookDetailRepository;

	@Mock
	private MemberRepository memberRepository;

	private LendService lendService;

	@BeforeEach
	void setUp() {
		lendService = new LendService(lendRepository, bookDetailRepository, memberRepository);
	}

	@Test
	void lendBookCreatesLendAndMarksBookDetailAsLent() {
		Book book = new Book();
		book.setId(10L);

		BookDetail bookDetail = new BookDetail();
		bookDetail.setId(20L);
		bookDetail.setBook(book);

		Member member = new Member();
		member.setId(30L);

		when(bookDetailRepository.findById(20L)).thenReturn(Optional.of(bookDetail));
		when(memberRepository.findById(30L)).thenReturn(Optional.of(member));

		LocalDate today = LocalDate.now();
		Long bookId = lendService.lendBook(20L, 30L);

		assertEquals(10L, bookId);
		assertTrue(bookDetail.isLent());

		ArgumentCaptor<Lend> lendCaptor = ArgumentCaptor.forClass(Lend.class);
		verify(lendRepository).save(lendCaptor.capture());
		Lend savedLend = lendCaptor.getValue();
		assertEquals(today, savedLend.getCreatedAt());
		assertEquals(savedLend.getCreatedAt().plusDays(15), savedLend.getReturnedDueAt());
		assertEquals(bookDetail, savedLend.getBookDetail());
		assertEquals(member, savedLend.getMember());
		verify(bookDetailRepository).save(bookDetail);
	}

	@Test
	void lendBookDoesNothingWhenBookIsAlreadyLent() {
		Book book = new Book();
		book.setId(10L);

		BookDetail bookDetail = new BookDetail();
		bookDetail.setBook(book);
		bookDetail.setLent(true);

		when(bookDetailRepository.findById(20L)).thenReturn(Optional.of(bookDetail));

		Long bookId = lendService.lendBook(20L, 30L);

		assertEquals(10L, bookId);
		verify(memberRepository, never()).findById(30L);
		verify(lendRepository, never()).save(any(Lend.class));
		verify(bookDetailRepository, never()).save(bookDetail);
	}

	@Test
	void canLendReturnsFalseForDisposedBook() {
		BookDetail bookDetail = new BookDetail();
		bookDetail.setDisposalAt(LocalDate.now());

		assertFalse(lendService.canLend(bookDetail));
	}

	@Test
	void returnBookRecordsDateAndMarksBookDetailAsAvailable() {
		BookDetail bookDetail = new BookDetail();
		bookDetail.setLent(true);

		Lend lend = new Lend();
		lend.setId(40L);
		lend.setBookDetail(bookDetail);

		when(lendRepository.findById(40L)).thenReturn(Optional.of(lend));
		LocalDate returnedAt = LocalDate.of(2026, 8, 20);

		lendService.returnBook(40L, returnedAt);

		assertEquals(returnedAt, lend.getReturnedAt());
		assertFalse(bookDetail.isLent());
		verify(lendRepository).save(lend);
		verify(bookDetailRepository).save(bookDetail);
	}
}
