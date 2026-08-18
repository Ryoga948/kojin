package jp.co.ctct.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.ctct.model.Book;
import jp.co.ctct.repository.BookRepository;
import jp.co.ctct.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BookController {

	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;

	@GetMapping("/book_list")
	public String showList(@RequestParam(required = false) Long category,
			@RequestParam(defaultValue = "") String isbn,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String author,
			@RequestParam(defaultValue = "") String publisher,
			@RequestParam(defaultValue = "0") int page, Model model) {
		Pageable pageable = PageRequest.of(page, 5);
		Page<Book> books = bookRepository.search(category, isbn, name, author, publisher, pageable);
		model.addAttribute("books", books);
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("category", category);
		model.addAttribute("isbn", isbn);
		model.addAttribute("name", name);
		model.addAttribute("author", author);
		model.addAttribute("publisher", publisher);
		model.addAttribute("main", "book/book_list::main");
		return "common/layout";
	}
}
