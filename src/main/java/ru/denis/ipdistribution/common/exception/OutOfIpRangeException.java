package ru.denis.ipdistribution.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OutOfIpRangeException extends RuntimeException {

  public OutOfIpRangeException(String message, Throwable cause) {
    super(message, cause);
  }
}
