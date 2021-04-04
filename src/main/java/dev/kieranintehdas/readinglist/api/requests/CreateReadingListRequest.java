package dev.kieranintehdas.readinglist.api.requests;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Value
public class CreateReadingListRequest {

  @NotBlank String name;

  Set<UUID> bookIds;

  public Set<UUID> getBookIds() {
    return Optional.ofNullable(bookIds).orElse(new HashSet<>());
  }
}
