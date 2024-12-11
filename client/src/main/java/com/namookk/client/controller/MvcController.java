package com.namookk.client.controller;

import com.namookk.client.client.mvc.MvcRestClient;
import com.namookk.client.client.mvc.UserRestClient;
import com.namookk.client.client.mvc.dto.CreateItemDto;
import com.namookk.client.client.mvc.dto.ItemDto;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mvc")
public class MvcController {

  private final MvcRestClient mvcRestClient;
  private final UserRestClient userRestClient;

  @GetMapping("/items")
  public ResponseEntity<List<ItemDto>> getItems() {
    return mvcRestClient.getItems();
  }

  @GetMapping("/items/{id}")
  public ResponseEntity<ItemDto> getItemInfo(@PathVariable Long id) {
    return mvcRestClient.getItemInfo(id);
  }

  @PostMapping("/items1")
  public ResponseEntity<ItemDto> cretaeItem1(@RequestBody Map<String, Object> request) {
    return mvcRestClient.saveItems(request);
  }

  @PostMapping("/items2")
  public ResponseEntity<ItemDto> createItem2(@RequestBody CreateItemDto request) {
    return mvcRestClient.saveItems2(request);
  }

  @PatchMapping("/items/{id}/price")
  public ResponseEntity<ItemDto> modifyItemPrice(@PathVariable Long id, @RequestParam Long price) {
    return mvcRestClient.modifyItemPrice(id, price);
  }

  @DeleteMapping("/items/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
    return mvcRestClient.deleteItem(id);
  }

  @GetMapping("/5xx")
  public ResponseEntity<Void> get5xx() {
    return mvcRestClient.get5xxError();
  }

  @GetMapping("/4xx")
  public ResponseEntity<Void> get4xx() {
    return mvcRestClient.get4xxError();
  }

  @GetMapping("/users/me")
  public ResponseEntity<Map<String, Object>> getUserInfo() {
    return userRestClient.getUserInfo();
  }
}
