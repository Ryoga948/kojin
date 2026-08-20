package jp.co.ctct.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ctct.model.BookDetail;
import jp.co.ctct.model.Lend;
import jp.co.ctct.model.Member;
import jp.co.ctct.repository.BookDetailRepository;
import jp.co.ctct.repository.LendRepository;
import jp.co.ctct.repository.MemberRepository;

@Service
public class LendService {

	private static final int LENDING_PERIOD_DAYS = 15;

	private final LendRepository lendRepository;
	private final BookDetailRepository bookDetailRepository;
	private final MemberRepository memberRepository;

	public LendService(LendRepository lendRepository, BookDetailRepository bookDetailRepository,
			MemberRepository memberRepository) {
		this.lendRepository = lendRepository;
		this.bookDetailRepository = bookDetailRepository;
		this.memberRepository = memberRepository;
	}

	public List<Lend> findLends(LocalDate returnedDueAt) {
		if (returnedDueAt == null) {
			return lendRepository.findAllByOrderByIdAsc();
		}
		return lendRepository.findByReturnedDueAtOrderByIdAsc(returnedDueAt);
	}

	public Lend findLend(Long id) {
		return lendRepository.findById(id).orElseThrow();
	}

	public BookDetail findBookDetail(Long id) {
		return bookDetailRepository.findById(id).orElseThrow();
	}

	public List<Member> findActiveMembers() {
		return memberRepository.findByDeletedAtIsNullOrderByIdAsc();
	}

	public LocalDate getDefaultReturnedDueAt() {
		return LocalDate.now().plusDays(LENDING_PERIOD_DAYS);
	}

	public boolean canLend(BookDetail bookDetail) {
		if (bookDetail.isLent()) {
			return false;
		}
		if (bookDetail.getDisposalAt() != null) {
			return false;
		}
		return true;
	}

	@Transactional
	public Long lendBook(Long bookDetailId, Long memberId) {
		BookDetail bookDetail = findBookDetail(bookDetailId);
		Long bookId = bookDetail.getBook().getId();
		if (!canLend(bookDetail)) {
			return bookId;
		}

		Member member = memberRepository.findById(memberId).orElseThrow();
		if (member.getDeletedAt() != null) {
			throw new IllegalStateException("退会済みの会員には貸し出せません。");
		}

		LocalDate today = LocalDate.now();
		Lend lend = new Lend();
		lend.setCreatedAt(today);
		lend.setReturnedDueAt(today.plusDays(LENDING_PERIOD_DAYS));
		lend.setBookDetail(bookDetail);
		lend.setMember(member);
		lendRepository.save(lend);

		bookDetail.setLent(true);
		bookDetailRepository.save(bookDetail);
		return bookId;
	}

	@Transactional
	public void returnBook(Long lendId, LocalDate returnedAt) {
		Lend lend = findLend(lendId);
		lend.setReturnedAt(returnedAt);
		lend.getBookDetail().setLent(false);
		lendRepository.save(lend);
		bookDetailRepository.save(lend.getBookDetail());
	}
}
