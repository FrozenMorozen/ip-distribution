package ru.denis.ipdistribution.exception.service;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OutOfIpRangeException extends RuntimeException {

  public OutOfIpRangeException(String message, Throwable cause) {
    super(message, cause);
  }
}
