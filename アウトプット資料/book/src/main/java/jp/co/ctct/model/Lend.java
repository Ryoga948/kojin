package jp.co.ctct.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lend {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate createdAt;

	private LocalDate returnedAt;

	private LocalDate returnedDueAt;

	@ManyToOne
	@JoinColumn(name = "book_detail_id")
	private BookDetail bookDetail;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
}
