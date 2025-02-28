package code.book.controller;

import code.book.model.Author;
import code.book.model.Book;
import code.book.model.BookRequest;
import code.book.repository.AuthorRepository;
import code.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;


    @PostMapping
    public ResponseEntity<?> createBook(@Validated @RequestBody BookRequest bookRequest) {
        Optional<Author> author = authorRepository.findById(bookRequest.getAuthorId());
        Optional<Book> findBook = bookRepository.findByIsbn(bookRequest.getIsbn());
        if (findBook.isPresent())
            return ResponseEntity.badRequest().body("이미 존재하는 isbn 입니다.");

        if (author.isPresent()) {
            Book book = new Book();
            book.setTitle(bookRequest.getTitle());
            book.setDescription(bookRequest.getDescription());
            book.setIsbn(bookRequest.getIsbn());
            book.setPublicationDate(bookRequest.getPublicationDate());
            book.setAuthor(author.get());
            return ResponseEntity.ok(bookRepository.save(book));
        }
        return ResponseEntity.badRequest().body("저자가 존재하지 않습니다.");
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return bookRepository.findById(id).map(book -> {
            bookRepository.delete(book);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateBook(@PathVariable Long id, @Validated @RequestBody Book book) {
//        return bookRepository.findById(id).map(findBook -> {
//            Optional<Book> existIsbn = bookRepository.findByIsbn(book.getIsbn());
//            if (existIsbn.isPresent() && !existIsbn.get().getId().equals(id)) {
//                return ResponseEntity.badRequest().body("이미 존재하는 Isbn 입니다.");
//            }
//            //Optional<Author> existAuthor = AuthorRepository.findById()
//
//
//            findBook.setTitle(book.getTitle());
//            findBook.setDescription(book.getDescription());
//            findBook.setIsbn(book.getIsbn());
//            findBook.setPublicationDate(book.getPublicationDate());
//            findBook.setAuthor(book.setAuthor()); // 저자가 존재하는지 확인해야 할텐ㅔㄱ
//            return ResponseEntity.ok(findBook);
//        }).orElse(ResponseEntity.notFound().build());
//    }
}