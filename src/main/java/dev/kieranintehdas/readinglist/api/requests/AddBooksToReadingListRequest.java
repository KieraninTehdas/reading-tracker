package dev.kieranintehdas.readinglist.api.requests;

import dev.kieranintehdas.readinglist.storage.Book;
import lombok.Value;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Value
public class AddBooksToReadingListRequest {

  Set<UUID> bookIds;

  Set<Book> books;

  public AddBooksToReadingListRequest(Set<UUID> bookIds, Set<Book> books) {
    this.bookIds = Optional.ofNullable(bookIds).orElse(Collections.emptySet());
    this.books = Optional.ofNullable(books).orElse(Collections.emptySet());
  }
}
