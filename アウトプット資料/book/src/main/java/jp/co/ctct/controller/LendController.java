package jp.co.ctct.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.ctct.model.BookDetail;
import jp.co.ctct.model.Lend;
import jp.co.ctct.model.Member;
import jp.co.ctct.repository.BookDetailRepository;
import jp.co.ctct.repository.LendRepository;
import jp.co.ctct.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LendController {

	private final LendRepository lendRepository;
	private final BookDetailRepository bookDetailRepository;
	private final MemberRepository memberRepository;

	@GetMapping("/lend_list")
	public String showList(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnedDueAt,
			Model model) {
		List<Lend> lends = returnedDueAt == null ? lendRepository.findAllByOrderByIdAsc()
				: lendRepository.findByReturnedDueAtOrderByIdAsc(returnedDueAt);
		model.addAttribute("lends", lends);
		model.addAttribute("returnedDueAt", returnedDueAt);
		model.addAttribute("main", "lend/lend_list::main");
		return "common/layout";
	}

	@GetMapping("/lend_add/{id}")
	public String showLendAdd(@PathVariable Long id, Model model) {
		BookDetail bookDetail = bookDetailRepository.findById(id).orElseThrow();
		if (bookDetail.isLent() || bookDetail.getDisposalAt() != null) {
			return "redirect:/book_detail/" + bookDetail.getBook().getId();
		}
		model.addAttribute("bookDetail", bookDetail);
		model.addAttribute("returnedDueAt", LocalDate.now().plusDays(15));
		model.addAttribute("members", memberRepository.findByDeletedAtIsNullOrderByIdAsc());
		model.addAttribute("main", "lend/lend_add::main");
		return "common/layout";
	}

	@Transactional
	@PostMapping("/lend_add")
	public String addLend(@RequestParam Long bookDetailId, @RequestParam Long memberId) {
		BookDetail bookDetail = bookDetailRepository.findById(bookDetailId).orElseThrow();
		Member member = memberRepository.findById(memberId)
				.filter(value -> value.getDeletedAt() == null).orElseThrow();
		if (bookDetail.isLent() || bookDetail.getDisposalAt() != null) {
			return "redirect:/book_detail/" + bookDetail.getBook().getId();
		}

		LocalDate today = LocalDate.now();
		Lend lend = new Lend();
		lend.setCreatedAt(today);
		lend.setReturnedDueAt(today.plusDays(15));
		lend.setBookDetail(bookDetail);
		lend.setMember(member);
		lendRepository.save(lend);

		bookDetail.setLent(true);
		bookDetailRepository.save(bookDetail);
		return "redirect:/book_detail/" + bookDetail.getBook().getId();
	}

	@GetMapping("/lend_edit/{id}")
	public String showLendEdit(@PathVariable Long id, Model model) {
		model.addAttribute("lend", lendRepository.findById(id).orElseThrow());
		model.addAttribute("main", "lend/lend_edit::main");
		return "common/layout";
	}

	@Transactional
	@PostMapping("/lend_return")
	public String returnBook(@RequestParam Long id,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnedAt,
			RedirectAttributes redirectAttributes) {
		Lend lend = lendRepository.findById(id).orElseThrow();
		lend.setReturnedAt(returnedAt);
		lend.getBookDetail().setLent(false);
		lendRepository.save(lend);
		bookDetailRepository.save(lend.getBookDetail());
		redirectAttributes.addFlashAttribute("message", "貸出情報を更新しました。");
		return "redirect:/lend_list";
	}
}
