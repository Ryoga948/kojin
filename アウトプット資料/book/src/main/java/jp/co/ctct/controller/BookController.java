package jp.co.ctct.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import jp.co.ctct.model.Book;
import jp.co.ctct.service.BookService;

@Controller
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/book_add")
	public String showBookAdd(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("categories", bookService.findAllCategories());
		model.addAttribute("main", "book/book_add::main");
		return "common/layout";
	}

	@PostMapping("/book_add")
	public String addBook(@Valid Book book, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("categories", bookService.findAllCategories());
			model.addAttribute("main", "book/book_add::main");
			return "common/layout";
		}
		bookService.addBook(book);
		redirectAttributes.addFlashAttribute("message", "書籍を追加しました。");
		return "redirect:/book_list";
	}

	@GetMapping("/book_edit/{id}")
	public String showBookEdit(@PathVariable Long id, Model model) {
		model.addAttribute("book", bookService.findBook(id));
		model.addAttribute("categories", bookService.findAllCategories());
		model.addAttribute("main", "book/book_edit::main");
		return "common/layout";
	}

	@PostMapping("/book_edit")
	public String editBook(@Valid Book book, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("categories", bookService.findAllCategories());
			model.addAttribute("main", "book/book_edit::main");
			return "common/layout";
		}
		bookService.updateBook(book);
		redirectAttributes.addFlashAttribute("message", "書籍情報を更新しました。");
		return "redirect:/book_detail/" + book.getId();
	}

}
