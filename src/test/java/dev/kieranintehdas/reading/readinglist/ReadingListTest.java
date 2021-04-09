package dev.kieranintehdas.reading.readinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.kieranintehdas.reading.book.Book;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class ReadingListTest {

  private Book createBook() {
    return Book.builder().id(UUID.randomUUID()).title("title").author("author").build();
  }

  private ReadingList createReadingList(Collection<Book> booksInList) {
    return ReadingList.builder().name("some name").books(new HashSet<>(booksInList)).build();
  }

  @Test
  public void addBooksToReadingList() {
    final Book initialBook = createBook();
    final Book bookToAdd = createBook();
    final Book anotherBookToAdd = createBook();
    final ReadingList initialReadingList =
        createReadingList(Collections.singletonList(initialBook));
    final ReadingList expectedReadingList =
        createReadingList(Arrays.asList(initialBook, bookToAdd, anotherBookToAdd));

    final ReadingList result =
        initialReadingList.addBooksToReadingList(
            Arrays.asList(bookToAdd, bookToAdd, anotherBookToAdd));

    assertEquals(expectedReadingList, result);
  }

  @Test
  public void addBooksToReadingList_whenListIsEmpty() {
    final Book bookToAdd = createBook();
    final ReadingList initialReadingList = createReadingList(Collections.emptySet());
    final ReadingList expectedReadingList = createReadingList(Collections.singleton(bookToAdd));

    final ReadingList result =
        initialReadingList.addBooksToReadingList(Collections.singleton(bookToAdd));

    assertEquals(expectedReadingList, result);
  }

  @Test
  public void removeBooksFromReadingList() {
    final Book bookToRemove = createBook();
    final Book anotherBookToRemove = createBook();
    final Book unrelatedBook = createBook();
    final ReadingList initialReadingList =
        createReadingList(Arrays.asList(bookToRemove, anotherBookToRemove));
    final ReadingList expectedReadingList = createReadingList(Collections.emptySet());

    final ReadingList result =
        initialReadingList.removeBooksFromReadingList(
            Arrays.asList(
                bookToRemove.getId(),
                bookToRemove.getId(),
                anotherBookToRemove.getId(),
                unrelatedBook.getId()));

    assertEquals(expectedReadingList, result);
  }
}
