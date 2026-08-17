package jp.co.ctct.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
	
	// コードの追加

	private LocalDate createdAt;
	
	@PrePersist
	public void prePersist() {
		createdAt = LocalDate.now();
	}

}
