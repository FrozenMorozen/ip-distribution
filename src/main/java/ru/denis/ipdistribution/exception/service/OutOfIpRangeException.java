package ru.denis.ipdistribution.exception.service;

public class OutOfIpRangeException extends RuntimeException {
  public OutOfIpRangeException(String errorMessage) {
    super(errorMessage);
  }
}
