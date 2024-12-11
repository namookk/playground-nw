package com.namookk.client.client.mvc;

import com.namookk.client.client.mvc.dto.CreateItemDto;
import com.namookk.client.client.mvc.dto.ItemDto;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;
import org.springframework.web.service.annotation.PostExchange;

@Component
@HttpExchange
public interface MvcRestClient {
  
  @GetExchange("/items")
  ResponseEntity<List<ItemDto>> getItems();

  @GetExchange("/items/{id}")
  ResponseEntity<ItemDto> getItemInfo(@PathVariable Long id);

  @PostExchange("/items")
  ResponseEntity<ItemDto> saveItems(@RequestBody Map<String, Object> body);

  @PostExchange("/items")
  ResponseEntity<ItemDto> saveItems2(@RequestBody CreateItemDto request);

  @PatchExchange("/items/{id}/price")
  ResponseEntity<ItemDto> modifyItemPrice(@PathVariable Long id, @RequestParam Long price);

  @DeleteExchange("/items/{id}/price")
  ResponseEntity<Void> deleteItem(@PathVariable Long id);

  @GetExchange("/5xx")
  ResponseEntity<Void> get5xxError();

  @GetExchange("/4xx")
  ResponseEntity<Void> get4xxError();
}
