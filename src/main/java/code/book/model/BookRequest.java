package code.book.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequest {
    @NotBlank(message = "도서 제목을 입력해주세요.")
    private String title;

    private String description;

    @NotBlank(message = "isbn을 입력해주세요.")
    private String isbn;

    private LocalDate publicationDate;

    @NotNull(message = "저자 id를 입력해주세요.")
    private Long authorId;
}
