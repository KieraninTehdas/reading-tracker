package dev.kieranintehdas.readinglist.api.responses;

import dev.kieranintehdas.readinglist.storage.Book;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Value
public class ReadingListDto {

  UUID id;
  String name;
  Set<AssociatedBook> associatedBooks;

  public ReadingListDto(UUID id, String name, Set<Book> associatedBooks) {
    this.id = id;
    this.name = name;
    this.associatedBooks =
        associatedBooks.stream()
            .map(
                book ->
                    AssociatedBook.builder()
                        .id(book.getId())
                        .author(book.getAuthor())
                        .title(book.getTitle())
                        .build())
            .collect(Collectors.toSet());
  }

  @Value
  @Builder
  static class AssociatedBook {
    UUID id;

    String title;

    String author;
  }
}
