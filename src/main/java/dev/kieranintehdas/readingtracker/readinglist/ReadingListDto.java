package dev.kieranintehdas.readingtracker.readinglist;

import dev.kieranintehdas.readingtracker.book.Book;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Value;

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
