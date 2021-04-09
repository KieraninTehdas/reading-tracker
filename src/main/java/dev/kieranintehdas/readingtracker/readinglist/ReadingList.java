package dev.kieranintehdas.readingtracker.readinglist;

import dev.kieranintehdas.readingtracker.book.Book;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

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
  @Builder.Default
  private Set<Book> books = new HashSet<>();

  public ReadingListDto constructDto() {
    return new ReadingListDto(id, name, books);
  }

  public ReadingList addBooksToReadingList(Collection<Book> booksToAdd) {
    books.addAll(new HashSet<>(booksToAdd));
    return this;
  }

  public ReadingList removeBooksFromReadingList(Collection<UUID> idsOfBooksToRemove) {
    final Set<UUID> uniqueBookIdsToRemove = new HashSet<>(idsOfBooksToRemove);
    final Set<Book> booksToRemove =
        books.stream()
            .filter(book -> uniqueBookIdsToRemove.contains(book.getId()))
            .collect(Collectors.toSet());
    books.removeAll(booksToRemove);
    return this;
  }
}
