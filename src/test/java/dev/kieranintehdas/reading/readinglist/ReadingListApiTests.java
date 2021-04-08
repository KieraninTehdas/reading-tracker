package dev.kieranintehdas.reading.readinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.when;

import dev.kieranintehdas.reading.book.Book;
import dev.kieranintehdas.reading.book.BookRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ReadingListApiTests {

  private final String readingListName = "My Reading List";
  private final ReadingList readingList =
      ReadingList.builder().name(readingListName).books(Collections.emptySet()).build();
  @Mock private ReadingListRepository readingListRepositoryMock;
  @Mock private BookRepository bookRepositoryMock;
  private ReadingListController controller;

  @BeforeEach
  void setUp() {
    final ReadingListManager readingListManager =
        new ReadingListManager(bookRepositoryMock, readingListRepositoryMock);
    controller = new ReadingListController(readingListManager);
  }

  private Book createBook(String title, String author) {
    return Book.builder().title(title).author(author).build();
  }

  private ReadingList createReadingList(Collection<Book> books) {
    return ReadingList.builder().name("My Reading List").books(new HashSet<>(books)).build();
  }

  @Test
  void createReadingList() {
    when(readingListRepositoryMock.save(any())).then(returnsFirstArg());

    final ResponseEntity<ReadingListDto> result =
        controller.createReadingList(
            new CreateReadingListRequest(readingListName, Collections.emptySet()));

    assertEquals(ResponseEntity.ok(readingList.constructDto()), result);
  }

  @Test
  void getReadingList() {
    when(readingListRepositoryMock.findById(any())).thenReturn(Optional.of(readingList));

    final ResponseEntity<ReadingListDto> result = controller.getReadingListById(UUID.randomUUID());

    assertEquals(ResponseEntity.ok(readingList.constructDto()), result);
  }

  @Test
  void getReadingList_whenNotFound() {
    when(readingListRepositoryMock.findById(any())).thenReturn(Optional.empty());

    final ResponseEntity<ReadingListDto> result = controller.getReadingListById(UUID.randomUUID());

    assertEquals(ResponseEntity.notFound().build(), result);
  }

  @Test
  void modifyReadingList_whenAddingBooks() {
    final Book book = createBook("Some Title", "Some Author");
    final ReadingList initialReadingList = createReadingList(Collections.emptySet());
    final ReadingList expectedReadingList = createReadingList(Collections.singleton(book));
    when(readingListRepositoryMock.findById(any())).thenReturn(Optional.of(initialReadingList));
    when(bookRepositoryMock.findAllById(anyIterable())).thenReturn(Collections.singletonList(book));
    when(readingListRepositoryMock.save(any())).thenAnswer(returnsFirstArg());

    final ResponseEntity<ReadingListDto> result =
        controller.modifyReadingList(
            UUID.randomUUID(),
            ModifyReadingListRequest.builder()
                .idsOfBooksToAdd(Collections.singleton(UUID.randomUUID()))
                .build());

    assertEquals(ResponseEntity.ok(expectedReadingList.constructDto()), result);
  }
}
