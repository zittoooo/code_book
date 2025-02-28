package code.book.controller;


import code.book.model.Author;
import code.book.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // 저자 생성
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(authorRepository.save(author));
    }

    // 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return authorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 저자 목록 조회
    @GetMapping
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @Validated @RequestBody Author authorInfo) {
        return authorRepository.findById(id).map(author -> {
            Optional<Author> existingEmail = authorRepository.findByEmail(authorInfo.getEmail());
            if (existingEmail.isPresent() && !existingEmail.get().getId().equals(id))
                return ResponseEntity.badRequest().body("이미 존재하는 이메일 입니다.");

            author.setName(authorInfo.getName());
            author.setEmail(authorInfo.getEmail());
            authorRepository.save(author);

            return ResponseEntity.ok(author);
        }).orElse(ResponseEntity.notFound().build());
    }

}
