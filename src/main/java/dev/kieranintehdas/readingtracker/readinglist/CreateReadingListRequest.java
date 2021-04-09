package dev.kieranintehdas.readingtracker.readinglist;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class CreateReadingListRequest {

  @NotBlank String name;

  Set<UUID> bookIds;

  public Set<UUID> getBookIds() {
    return Optional.ofNullable(bookIds).orElse(new HashSet<>());
  }
}
