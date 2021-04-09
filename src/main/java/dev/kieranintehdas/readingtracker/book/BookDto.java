package dev.kieranintehdas.readingtracker.book;

import dev.kieranintehdas.readingtracker.readinglist.ReadingList;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class BookDto {

  UUID id;
  String title;
  String author;
  Set<AssociatedReadingList> associatedReadingLists;

  @Builder
  public BookDto(
      @NonNull UUID id,
      @NonNull String title,
      @NonNull String author,
      @NonNull Set<ReadingList> readingLists) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.associatedReadingLists =
        readingLists.stream()
            .map(
                readingList ->
                    new AssociatedReadingList(readingList.getId(), readingList.getName()))
            .collect(Collectors.toSet());
  }

  @Value
  static class AssociatedReadingList {
    UUID id;
    String name;
  }
}
