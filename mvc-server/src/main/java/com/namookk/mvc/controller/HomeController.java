package com.namookk.mvc.controller;

import com.namookk.mvc.config.exception.DataNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/5xx")
  public ResponseEntity<Void> return500Status() {
    throw new RuntimeException("500 에러 발생");
  }

  @GetMapping("/4xx")
  public ResponseEntity<Void> return400Status() {
    throw new DataNotFoundException("404 에러 발생");
  }
}
