package dev.kieranintehdas.readinglist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookApiTest {

    private BookController bookController;
    @Mock
    private BookRepository bookRepository;

    private final UUID id = UUID.randomUUID();
    private final String title = "The Bible";
    private final String author = "God, Judaeo-Christian";
    private final Book book = Book.builder()
            .id(id)
            .author(author)
            .title(title)
            .build();

    @BeforeEach
    public void setup() {
        bookController = new BookController(bookRepository);
    }

    @Test
    public void createBook() {
        when(bookRepository.save(any())).then(
                invocation -> {
                    final Book bookToSave = invocation.getArgument(0);
                    return Book.builder()
                            .id(id)
                            .title(bookToSave.getTitle())
                            .author(bookToSave.getAuthor())
                            .build();
                }
        );
        final ResponseEntity<Book> expectedResult = ResponseEntity.ok(
                Book.builder()
                .author(author)
                .id(id)
                .title(title)
                .build()
        );

        final ResponseEntity<Book> result = bookController.createBook(
                CreateBookRequest.builder()
                .title(title)
                .author(author)
                .build()
        );

        assertEquals(expectedResult, result);
    }

    @Test
    public void getBookById() {
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        final ResponseEntity<Book> result = bookController.getBookById(id);

        assertEquals(ResponseEntity.ok(book), result);
    }

    @Test
    public void getBookById_whenNotFound() {
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        final ResponseEntity<Book> result = bookController.getBookById(id);

        assertEquals(ResponseEntity.notFound().build(), result);
    }

}
