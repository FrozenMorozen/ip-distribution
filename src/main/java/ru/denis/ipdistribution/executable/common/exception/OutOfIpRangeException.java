package ru.denis.ipdistribution.executable.common.exception;

public class OutOfIpRangeException extends RuntimeException {
  public OutOfIpRangeException(String message) {
    super(message);
  }
}
