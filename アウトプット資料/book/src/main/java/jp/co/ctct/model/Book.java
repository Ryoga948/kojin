package jp.co.ctct.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "{book.isbn.required}")
	private String isbn;

	@NotBlank(message = "{book.name.required}")
	private String name;

	@NotBlank(message = "{book.author.required}")
	private String author;

	@NotBlank(message = "{book.publisher.required}")
	private String publisher;

	@NotNull(message = "{book.publishedAt.required}")
	private LocalDate publishedAt;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
}
