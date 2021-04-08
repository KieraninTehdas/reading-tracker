package dev.kieranintehdas.reading;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

  private final String notFoundId;
  private final Class<?> notFoundType;

  public NotFoundException(String notFoundId, Class<?> notFoundType) {
    this.notFoundType = notFoundType;
    this.notFoundId = notFoundId;
  }
}
