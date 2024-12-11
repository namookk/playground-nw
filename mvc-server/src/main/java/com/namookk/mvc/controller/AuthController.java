package com.namookk.mvc.controller;

import com.namookk.mvc.entity.JwtToken;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @PostMapping("/token")
  public ResponseEntity<UUID> generateToken() {
    JwtToken.getInstance().generateToken();
    return ResponseEntity.ok(JwtToken.getInstance().getToken());
  }
}
