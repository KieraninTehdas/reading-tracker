package dev.kieranintehdas.readinglist.api.requests;

import dev.kieranintehdas.readinglist.storage.Book;
import lombok.Value;

import java.util.*;

@Value
public class ModifyReadingListRequest {

    Set<UUID> bookIdsToAdd;

    Set<UUID> bookIdsToRemove;

    Set<Book> booksToAdd;

    Set<Book> booksToRemove;

    public Set<UUID> getBookIdsToAdd() {
        return getOrDefaultEmptySet(bookIdsToAdd);
    }

    public Set<UUID> getBookIdsToRemove() {
        return getOrDefaultEmptySet(bookIdsToRemove);
    }

    public Set<Book> getBooksToAdd() {
        return getOrDefaultEmptySet(booksToAdd);
    }

    public Set<Book> getBooksToRemove() {
        return getOrDefaultEmptySet(booksToRemove);
    }

    private <T> Set<T> getOrDefaultEmptySet(Set<T> fieldToGetOrDefault) {
        return Optional.ofNullable(fieldToGetOrDefault).orElse(Collections.emptySet());
    }
}
