package dev.kieranintehdas.readinglist.api.controllers;

import dev.kieranintehdas.readinglist.api.requests.CreateBookRequest;
import dev.kieranintehdas.readinglist.storage.Book;
import dev.kieranintehdas.readinglist.storage.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("books")
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid final CreateBookRequest createBookRequest) {
        final Book savedBook = bookRepository.save(
                Book.builder()
                .title(createBookRequest.getTitle())
                .author(createBookRequest.getAuthor())
                .build()
        );

        return ResponseEntity.ok(savedBook);
    }

    @GetMapping("{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID bookId) {
        return bookRepository.findById(bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
