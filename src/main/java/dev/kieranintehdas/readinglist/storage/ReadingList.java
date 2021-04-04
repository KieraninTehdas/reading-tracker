package dev.kieranintehdas.readinglist.storage;

import dev.kieranintehdas.readinglist.api.responses.ReadingListDto;
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

    @Id
    @GeneratedValue
    private UUID id;

    @NonNull
    @NotBlank
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "reading_list_books",
            joinColumns = {@JoinColumn(name = "reading_list_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private Set<Book> books;

    public ReadingListDto constructDto() {
        return new ReadingListDto(id, name, books);
    }

}
