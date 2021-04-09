package dev.kieranintehdas.readingtracker.readinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.kieranintehdas.readingtracker.book.Book;
import dev.kieranintehdas.readingtracker.book.BookRepository;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
    return Book.builder().id(UUID.randomUUID()).title(title).author(author).build();
  }

  private ReadingList createReadingList(Collection<Book> books) {
    return ReadingList.builder().name("My Reading List").books(new HashSet<>(books)).build();
  }

  private <T> Set<T> collectionToSet(final Collection<T> collection) {
    return new HashSet<>(collection);
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

  @Test
  void modifyReadingList_whenRemovingBooks() {
    final Book bookToRemove = createBook("Some Title", "Some Author");
    final ReadingList initialReadingList = createReadingList(Collections.singleton(bookToRemove));
    final ReadingList expectedReadingList = createReadingList(Collections.emptySet());
    when(readingListRepositoryMock.findById(any())).thenReturn(Optional.of(initialReadingList));
    when(readingListRepositoryMock.save(any())).thenAnswer(returnsFirstArg());

    final ResponseEntity<ReadingListDto> result =
        controller.modifyReadingList(
            UUID.randomUUID(),
            ModifyReadingListRequest.builder()
                .idsOfBooksToRemove(Collections.singleton(bookToRemove.getId()))
                .build());

    assertEquals(ResponseEntity.ok(expectedReadingList.constructDto()), result);
  }

  @Test
  void modifyReadingList_whenAddingAndRemovingBooks() {
    final Book bookToRemove = createBook("I get removed", "I do");
    final Book unchangedBook = createBook("I don't change", "I don't");
    final Book bookToAdd = createBook("I got added", "I did");
    final Book nonExistentBook = createBook("I", "Don't Exist");
    final ReadingList initialReadingList =
        createReadingList(Arrays.asList(bookToRemove, unchangedBook));
    final ReadingList expectedReadingList =
        createReadingList(Arrays.asList(unchangedBook, bookToAdd));
    when(readingListRepositoryMock.findById(any())).thenReturn(Optional.of(initialReadingList));
    when(readingListRepositoryMock.save(any())).thenAnswer(returnsFirstArg());
    when(bookRepositoryMock.findAllById(anyIterable()))
        .thenReturn(Collections.singletonList(bookToAdd));

    final ResponseEntity<ReadingListDto> result =
        controller.modifyReadingList(
            UUID.randomUUID(),
            ModifyReadingListRequest.builder()
                .idsOfBooksToRemove(
                    collectionToSet(Arrays.asList(bookToRemove.getId(), unchangedBook.getId())))
                .idsOfBooksToAdd(
                    collectionToSet(
                        Arrays.asList(
                            unchangedBook.getId(), bookToAdd.getId(), nonExistentBook.getId())))
                .build());

    assertEquals(ResponseEntity.ok(expectedReadingList.constructDto()), result);
    verify(bookRepositoryMock)
        .findAllById(collectionToSet(Arrays.asList(bookToAdd.getId(), nonExistentBook.getId())));
  }
}
