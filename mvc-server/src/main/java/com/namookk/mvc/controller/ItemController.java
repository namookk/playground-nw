package com.namookk.mvc.controller;

import com.namookk.mvc.model.dto.CreateItemDto;
import com.namookk.mvc.model.dto.ItemDto;
import com.namookk.mvc.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/items")
public class ItemController {

  private final ItemService itemService;

  @GetMapping
  public ResponseEntity<List<ItemDto>> getItems() {
    return ResponseEntity.ok(itemService.getItems());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemDto> getItemInfo(@PathVariable Long id) {
    return ResponseEntity.ok(itemService.getItemInfo(id));
  }

  @PostMapping
  public ResponseEntity<ItemDto> saveItem(@RequestBody CreateItemDto request) {
    return new ResponseEntity<>(itemService.saveItem(request), HttpStatus.CREATED);
  }

  @PatchMapping("/{id}/price")
  public ResponseEntity<ItemDto> changePrice(@PathVariable Long id, @RequestParam Long price) {
    return ResponseEntity.ok(itemService.changePrice(id, price));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
    itemService.deleteItem(id);
    return ResponseEntity.noContent().build();
  }
}
