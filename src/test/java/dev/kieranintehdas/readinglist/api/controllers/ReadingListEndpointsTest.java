package dev.kieranintehdas.readinglist.api.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import dev.kieranintehdas.readinglist.api.NotFoundException;
import dev.kieranintehdas.readinglist.api.ReadingListManager;
import dev.kieranintehdas.readinglist.api.requests.AddBooksToReadingListRequest;
import dev.kieranintehdas.readinglist.api.requests.CreateReadingListRequest;
import dev.kieranintehdas.readinglist.api.responses.ReadingListDto;
import dev.kieranintehdas.readinglist.storage.Book;
import dev.kieranintehdas.readinglist.storage.BookRepository;
import dev.kieranintehdas.readinglist.storage.ReadingList;
import dev.kieranintehdas.readinglist.storage.ReadingListRepository;
import java.util.Arrays;
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
class ReadingListEndpointsTest {

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

  private ReadingList createReadingList(String name, Collection<Book> books) {
    return ReadingList.builder().name(name).books(new HashSet<>(books)).build();
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
  void addBooksToReadingList() {
    final Book bookAlreadyInList = createBook("Already In", "The List");
    final Book addedBook = createBook("Added", "By Id");
    final ReadingList readingList =
        createReadingList(readingListName, Collections.singleton(bookAlreadyInList));
    when(readingListRepositoryMock.findById(any())).thenReturn(Optional.of(readingList));
    when(readingListRepositoryMock.save(any())).thenAnswer(returnsFirstArg());
    when(bookRepositoryMock.findAllById(any())).thenReturn(Collections.singletonList(addedBook));
    final ReadingList expectedReadingList =
        createReadingList(readingListName, Arrays.asList(bookAlreadyInList, addedBook));

    final ResponseEntity<ReadingListDto> result =
        controller.addBooksToReadingList(
            UUID.randomUUID(),
            new AddBooksToReadingListRequest(Collections.singleton(UUID.randomUUID())));

    assertEquals(ResponseEntity.ok(expectedReadingList.constructDto()), result);
  }

  @Test
  void addBooksToReadingList_whenReadingListNotFound() {
    when(readingListRepositoryMock.findById(any())).thenReturn(Optional.empty());

    assertThrows(
        NotFoundException.class,
        () ->
            controller.addBooksToReadingList(
                UUID.randomUUID(), new AddBooksToReadingListRequest(Collections.emptySet())));
  }

  @Test
  void addBooksToReadingList_whenBookNotFound() {
    final ReadingList readingList = createReadingList(readingListName, Collections.emptySet());
    final Book book = createBook("title", "author");
    when(readingListRepositoryMock.findById(any())).thenReturn(Optional.of(readingList));
    when(readingListRepositoryMock.save(any())).thenAnswer(returnsFirstArg());
    when(bookRepositoryMock.findAllById(any())).thenReturn(Collections.singletonList(book));
    final ReadingList expectedReadingList =
        createReadingList(readingListName, Collections.singleton(book));

    final ResponseEntity<ReadingListDto> result =
        controller.addBooksToReadingList(
            UUID.randomUUID(),
            new AddBooksToReadingListRequest(
                new HashSet<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()))));

    assertEquals(ResponseEntity.ok(expectedReadingList.constructDto()), result);
  }


}
