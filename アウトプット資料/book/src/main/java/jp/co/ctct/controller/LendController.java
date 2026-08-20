package jp.co.ctct.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.ctct.model.BookDetail;
import jp.co.ctct.service.LendService;

@Controller
public class LendController {

	private final LendService lendService;

	public LendController(LendService lendService) {
		this.lendService = lendService;
	}

	@GetMapping("/lend_add/{id}")
	public String showLendAdd(@PathVariable Long id, Model model) {
		BookDetail bookDetail = lendService.findBookDetail(id);
		if (!lendService.canLend(bookDetail)) {
			return "redirect:/book_detail/" + bookDetail.getBook().getId();
		}
		model.addAttribute("bookDetail", bookDetail);
		model.addAttribute("returnedDueAt", lendService.getDefaultReturnedDueAt());
		model.addAttribute("members", lendService.findActiveMembers());
		model.addAttribute("main", "lend/lend_add::main");
		return "common/layout";
	}

	@PostMapping("/lend_add")
	public String addLend(@RequestParam Long bookDetailId, @RequestParam Long memberId) {
		Long bookId = lendService.lendBook(bookDetailId, memberId);
		return "redirect:/book_detail/" + bookId;
	}

	@GetMapping("/lend_edit/{id}")
	public String showLendEdit(@PathVariable Long id, Model model) {
		model.addAttribute("lend", lendService.findLend(id));
		model.addAttribute("main", "lend/lend_edit::main");
		return "common/layout";
	}

	@PostMapping("/lend_return")
	public String returnBook(@RequestParam Long id,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnedAt,
			RedirectAttributes redirectAttributes) {
		lendService.returnBook(id, returnedAt);
		redirectAttributes.addFlashAttribute("message", "貸出情報を更新しました。");
		return "redirect:/lend_list";
	}
}
