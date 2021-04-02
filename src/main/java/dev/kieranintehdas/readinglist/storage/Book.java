package dev.kieranintehdas.readinglist.storage;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "books")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue
    private UUID id;

    @NonNull
    @NotBlank
    private String title;

    @NonNull
    @NotBlank
    private String author;

    @ToString.Exclude
    @ManyToMany(mappedBy = "books")
    private Set<ReadingList> readingLists;

    public Set<ReadingList> getReadingLists() {
        return Optional.ofNullable(readingLists)
                .orElse(new HashSet<>());
    }
}
