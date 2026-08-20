package jp.co.ctct.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.ctct.model.Book;
import jp.co.ctct.service.BookDetailService;
import jp.co.ctct.service.BookService;

@Controller
public class BookListController {

	private final BookService bookService;
	private final BookDetailService bookDetailService;

	public BookListController(BookService bookService, BookDetailService bookDetailService) {
		this.bookService = bookService;
		this.bookDetailService = bookDetailService;
	}

	@GetMapping("/book_list")
	public String showList(@RequestParam(required = false) Long category,
			@RequestParam(defaultValue = "") String isbn,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String author,
			@RequestParam(defaultValue = "") String publisher,
			@RequestParam(defaultValue = "0") int page, Model model) {
		Page<Book> books = bookService.searchBooks(category, isbn, name, author, publisher, page);
		model.addAttribute("books", books);
		model.addAttribute("categories", bookService.findAllCategories());
		model.addAttribute("category", category);
		model.addAttribute("isbn", isbn);
		model.addAttribute("name", name);
		model.addAttribute("author", author);
		model.addAttribute("publisher", publisher);
		model.addAttribute("main", "book/book_list::main");
		return "common/layout";
	}

	@GetMapping("/book_detail/{id}")
	public String showDetail(@PathVariable Long id, Model model) {
		model.addAttribute("book", bookService.findBook(id));
		model.addAttribute("bookDetails", bookDetailService.findByBookId(id));
		model.addAttribute("main", "book/book_detail::main");
		return "common/layout";
	}
}
