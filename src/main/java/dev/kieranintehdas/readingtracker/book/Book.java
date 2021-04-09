package dev.kieranintehdas.readingtracker.book;

import dev.kieranintehdas.readingtracker.readinglist.ReadingList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "books")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

  @Id @GeneratedValue private UUID id;

  @NonNull @NotBlank private String title;

  @NonNull @NotBlank private String author;

  @ToString.Exclude
  @ManyToMany(mappedBy = "books")
  private Set<ReadingList> readingLists;

  public Set<ReadingList> getReadingLists() {
    return Optional.ofNullable(readingLists).orElse(new HashSet<>());
  }

  public BookDto constructDto() {
    return BookDto.builder()
        .id(id)
        .title(title)
        .author(author)
        .readingLists(getReadingLists())
        .build();
  }
}
