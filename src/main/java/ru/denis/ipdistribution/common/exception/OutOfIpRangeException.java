package ru.denis.ipdistribution.common.exception;

public class OutOfIpRangeException extends RuntimeException {
  public OutOfIpRangeException(String message) {
    super(message);
  }
}
