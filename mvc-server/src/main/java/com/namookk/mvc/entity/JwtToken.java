package com.namookk.mvc.entity;

import java.util.UUID;

public class JwtToken {

  private UUID token;

  private JwtToken() {}

  private static class JwtTokenHolder {
    private static final JwtToken JWT_TOKEN = new JwtToken();
  }

  public static JwtToken getInstance() {
    return JwtTokenHolder.JWT_TOKEN;
  }

  public void generateToken() {
    this.token = UUID.randomUUID();
  }

  public boolean validToken(UUID token) {
    if(this.token == null) {
      return false;
    }

    return this.token.equals(token);
  }

  public UUID getToken() {
    return this.token;
  }
}
