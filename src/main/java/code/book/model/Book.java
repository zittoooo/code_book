package code.book.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "isbn"))
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "도서 제목을 입력해주세요.")
    private String title;

    private String description;
    @NotBlank(message = "ISBN을 입력해주세요.")
    @Pattern(regexp = "^\\d{9}0$", message = "ISBN-10 형식을 따라주세요.")
    @Column(unique = true)
    private String isbn;

    private LocalDate publicationDate;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}
