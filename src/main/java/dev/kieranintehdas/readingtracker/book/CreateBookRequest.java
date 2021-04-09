package dev.kieranintehdas.readingtracker.book;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateBookRequest {

  @NotBlank String title;

  @NotBlank String author;
}
