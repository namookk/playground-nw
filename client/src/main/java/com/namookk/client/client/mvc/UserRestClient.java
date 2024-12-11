package com.namookk.client.client.mvc;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@Component
@HttpExchange("/users")
public interface UserRestClient {
  
  @GetExchange("/me")
  ResponseEntity<Map<String, Object>> getUserInfo();
}
