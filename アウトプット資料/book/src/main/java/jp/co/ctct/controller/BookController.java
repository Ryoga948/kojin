package jp.co.ctct.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import jp.co.ctct.repository.BookDetailRepository;
import jp.co.ctct.repository.BookRepository;
import jp.co.ctct.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BookController {

	private final BookRepository bookRepository;
	private final BookDetailRepository bookDetailRepository;
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

	@GetMapping("/book_detail/{id}")
	public String showDetail(@PathVariable Long id, Model model) {
		model.addAttribute("book", bookRepository.findById(id).orElseThrow());
		model.addAttribute("bookDetails", bookDetailRepository.findByBookIdOrderByIdAsc(id));
		model.addAttribute("main", "book/book_detail::main");
		return "common/layout";
	}

	@GetMapping("/book_edit/{id}")
	public String showBookEdit(@PathVariable Long id, Model model) {
		model.addAttribute("book", bookRepository.findById(id).orElseThrow());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("main", "book/book_edit::main");
		return "common/layout";
	}

	@PostMapping("/book_edit")
	public String editBook(@Valid Book book, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("main", "book/book_edit::main");
			return "common/layout";
		}
		bookRepository.save(book);
		redirectAttributes.addFlashAttribute("message", "書籍情報を更新しました。");
		return "redirect:/book_detail/" + book.getId();
	}

	@GetMapping("/bookdetail_add/{id}")
	public String showBookDetailAdd(@PathVariable Long id, Model model) {
		Book book = bookRepository.findById(id).orElseThrow();
		model.addAttribute("book", book);
		model.addAttribute("bookDetail", new BookDetail());
		model.addAttribute("main", "book/bookdetail_add::main");
		return "common/layout";
	}

	@PostMapping("/bookdetail_add")
	public String addBookDetail(@RequestParam Long bookId, @Valid BookDetail bookDetail,
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		Book book = bookRepository.findById(bookId).orElseThrow();
		if (result.hasErrors()) {
			model.addAttribute("book", book);
			model.addAttribute("main", "book/bookdetail_add::main");
			return "common/layout";
		}
		bookDetail.setBook(book);
		bookDetailRepository.save(bookDetail);
		redirectAttributes.addFlashAttribute("message", "書籍詳細を追加しました。");
		return "redirect:/book_detail/" + bookId;
	}

	@GetMapping("/bookdetail_edit/{id}")
	public String showBookDetailEdit(@PathVariable Long id, Model model) {
		model.addAttribute("bookDetail", bookDetailRepository.findById(id).orElseThrow());
		model.addAttribute("main", "book/bookdetail_edit::main");
		return "common/layout";
	}

	@PostMapping("/bookdetail_edit")
	public String editBookDetail(@Valid BookDetail bookDetail, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		BookDetail current = bookDetailRepository.findById(bookDetail.getId()).orElseThrow();
		if (result.hasErrors()) {
			bookDetail.setBook(current.getBook());
			model.addAttribute("main", "book/bookdetail_edit::main");
			return "common/layout";
		}
		current.setStartAt(bookDetail.getStartAt());
		current.setDisposalAt(bookDetail.getDisposalAt());
		current.setMemo(bookDetail.getMemo());
		bookDetailRepository.save(current);
		redirectAttributes.addFlashAttribute("message", "書籍詳細情報を更新しました。");
		return "redirect:/book_detail/" + current.getBook().getId();
	}
}
