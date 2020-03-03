package ru.denis.ipdistribution.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidIpException extends RuntimeException {
  public InvalidIpException(String message) {
    super(message);
  }
}
