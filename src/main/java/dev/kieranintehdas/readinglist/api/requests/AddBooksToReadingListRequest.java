package dev.kieranintehdas.readinglist.api.requests;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.Value;

@Value
public class AddBooksToReadingListRequest {

  Set<UUID> bookIds;

  public AddBooksToReadingListRequest(Set<UUID> bookIds) {
    this.bookIds = Optional.ofNullable(bookIds).orElse(Collections.emptySet());
  }
}
