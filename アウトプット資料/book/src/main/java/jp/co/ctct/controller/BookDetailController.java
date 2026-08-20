package jp.co.ctct.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import jp.co.ctct.model.Book;
import jp.co.ctct.model.BookDetail;
import jp.co.ctct.service.BookDetailService;
import jp.co.ctct.service.BookService;

@Controller
public class BookDetailController {

	private final BookService bookService;
	private final BookDetailService bookDetailService;

	public BookDetailController(BookService bookService, BookDetailService bookDetailService) {
		this.bookService = bookService;
		this.bookDetailService = bookDetailService;
	}

	@GetMapping("/bookdetail_add/{id}")
	public String showBookDetailAdd(@PathVariable Long id, Model model) {
		Book book = bookService.findBook(id);
		model.addAttribute("book", book);
		model.addAttribute("bookDetail", new BookDetail());
		model.addAttribute("main", "book/bookdetail_add::main");
		return "common/layout";
	}

	@PostMapping("/bookdetail_add")
	public String addBookDetail(@RequestParam Long bookId, @Valid BookDetail bookDetail,
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		Book book = bookService.findBook(bookId);
		if (result.hasErrors()) {
			model.addAttribute("book", book);
			model.addAttribute("main", "book/bookdetail_add::main");
			return "common/layout";
		}

		bookDetailService.addBookDetail(book, bookDetail);
		redirectAttributes.addFlashAttribute("message", "書籍詳細を追加しました。");
		return "redirect:/book_detail/" + bookId;
	}

	@GetMapping("/bookdetail_edit/{id}")
	public String showBookDetailEdit(@PathVariable Long id, Model model) {
		model.addAttribute("bookDetail", bookDetailService.findBookDetail(id));
		model.addAttribute("main", "book/bookdetail_edit::main");
		return "common/layout";
	}

	@PostMapping("/bookdetail_edit")
	public String editBookDetail(@Valid BookDetail bookDetail, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		BookDetail currentBookDetail = bookDetailService.findBookDetail(bookDetail.getId());
		if (result.hasErrors()) {
			bookDetail.setBook(currentBookDetail.getBook());
			model.addAttribute("main", "book/bookdetail_edit::main");
			return "common/layout";
		}

		bookDetailService.updateBookDetail(currentBookDetail, bookDetail);
		redirectAttributes.addFlashAttribute("message", "書籍詳細情報を更新しました。");
		return "redirect:/book_detail/" + currentBookDetail.getBook().getId();
	}
}
