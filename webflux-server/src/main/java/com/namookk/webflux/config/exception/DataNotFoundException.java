package com.namookk.webflux.config.exception;

public class DataNotFoundException extends RuntimeException{
  public DataNotFoundException(String message) {
    super(message);
  }
}
