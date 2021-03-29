package dev.kieranintehdas.readinglist;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class CreateBookRequest {

    @NotBlank
    String title;

    @NotBlank
    String author;
}
