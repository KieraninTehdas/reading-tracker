package dev.kieranintehdas.reading.readinglist;

import dev.kieranintehdas.reading.book.Book;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "reading_lists")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadingList {

  @Id @GeneratedValue private UUID id;

  @NonNull @NotBlank private String name;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "reading_list_books",
      joinColumns = {@JoinColumn(name = "reading_list_id")},
      inverseJoinColumns = {@JoinColumn(name = "book_id")})
  private Set<Book> books;

  public ReadingListDto constructDto() {
    return new ReadingListDto(id, name, books);
  }

  public ReadingList addBooksToReadingList(Collection<Book> booksToAdd) {
    books.addAll(new HashSet<>(booksToAdd));
    return this;
  }

  public ReadingList removeBooksFromReadingList(Collection<UUID> idsOfBooksToRemove) {
    final Set<UUID> uniqueBookIdsToRemove = new HashSet<>(idsOfBooksToRemove);
    final Set<Book> booksToRemove = books.stream()
        .filter(book -> uniqueBookIdsToRemove.contains(book.getId()))
        .collect(Collectors.toSet());
    books.removeAll(booksToRemove);
    return this;
  }
}
