package ru.denis.ipdistribution.executable.exception;

public class OutOfIpRangeException extends RuntimeException {
  public OutOfIpRangeException(String message) {
    super(message);
  }
}
