package jp.co.ctct.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
	
	// コードの追加
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 2, max = 255, message = "{member.name.size}")
	private String name;

	@NotBlank(message = "{member.email.required}")
	@Email(message = "{member.email.format}")
	private String email;

	@NotBlank(message = "{member.address.required}")
	private String address;

	@NotNull(message = "{member.birth.required}")
	private LocalDate birth;

	private LocalDate deletedAt;

	@NotBlank(message = "{member.phone.required}")
	private String phone;

	@NotBlank(message = "{member.zipCode.required}")
	private String zipCode;

	private LocalDate createdAt;
	
	@PrePersist
	public void prePersist() {
		createdAt = LocalDate.now();
	}

}
