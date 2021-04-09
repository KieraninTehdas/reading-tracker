package dev.kieranintehdas.readingtracker.book;

import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("books")
@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookRepository bookRepository;

  @PostMapping
  public ResponseEntity<BookDto> createBook(
      @RequestBody @Valid final CreateBookRequest createBookRequest) {
    final Book savedBook =
        bookRepository.save(
            Book.builder()
                .title(createBookRequest.getTitle())
                .author(createBookRequest.getAuthor())
                .build());

    return ResponseEntity.ok(savedBook.constructDto());
  }

  @GetMapping("{bookId}")
  public ResponseEntity<BookDto> getBookById(@PathVariable UUID bookId) {
    return bookRepository
        .findById(bookId)
        .map(book -> ResponseEntity.ok(book.constructDto()))
        .orElse(ResponseEntity.notFound().build());
  }
}
