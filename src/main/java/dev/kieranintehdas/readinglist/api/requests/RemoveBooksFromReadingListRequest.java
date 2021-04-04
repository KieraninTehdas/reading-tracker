package dev.kieranintehdas.readinglist.api.requests;

import lombok.Value;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Value
public class RemoveBooksFromReadingListRequest {

    Set<UUID> bookIds;

    public RemoveBooksFromReadingListRequest() {
        this.bookIds = Collections.emptySet();
    }

    public RemoveBooksFromReadingListRequest(Set<UUID> bookIds) {
        this.bookIds = Optional.ofNullable(bookIds).orElse(Collections.emptySet());
    }
}
