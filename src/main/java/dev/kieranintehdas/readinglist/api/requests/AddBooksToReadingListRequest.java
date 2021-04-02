package dev.kieranintehdas.readinglist.api.requests;

import dev.kieranintehdas.readinglist.storage.Book;
import lombok.Value;

import java.util.*;

@Value
public class AddBooksToReadingListRequest {

    Set<UUID> bookIdsToAdd;

    Set<Book> booksToAdd;

    public AddBooksToReadingListRequest(Set<UUID> bookIdsToAdd, Set<Book> booksToAdd) {
        this.bookIdsToAdd = Optional.ofNullable(bookIdsToAdd).orElse(Collections.emptySet());
        this.booksToAdd = Optional.ofNullable(booksToAdd).orElse(Collections.emptySet());
    }

}
