package dev.kieranintehdas.readingtracker.readinglist;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ModifyReadingListRequest {

  Set<UUID> idsOfBooksToAdd;

  Set<UUID> idsOfBooksToRemove;

  public ModifyReadingListRequest(
      Collection<UUID> idsOfBooksToAdd, Collection<UUID> idsOfBooksToRemove) {
    this.idsOfBooksToAdd = createSetOrDefaultEmpty(idsOfBooksToAdd);
    this.idsOfBooksToRemove = createSetOrDefaultEmpty(idsOfBooksToRemove);
  }

  private Set<UUID> createSetOrDefaultEmpty(Collection<UUID> collection) {
    return Optional.ofNullable(collection).map(HashSet::new).orElse(new HashSet<>());
  }
}
