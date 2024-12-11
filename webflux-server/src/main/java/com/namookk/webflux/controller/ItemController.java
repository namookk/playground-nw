package com.namookk.webflux.controller;

import com.namookk.webflux.model.dto.CreateItemDto;
import com.namookk.webflux.model.dto.ItemDto;
import com.namookk.webflux.service.ItemService;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

  private final ItemService itemService;

  @GetMapping
  public Flux<ItemDto> getItems() {
    return itemService.getItems();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<ItemDto>> getItemInfo(@PathVariable Long id) {
    return itemService.getItemInfo(id)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<ItemDto>> saveItem(@RequestBody CreateItemDto request) {
    return itemService.saveItem(request)
        .map(item -> new ResponseEntity<>(item, HttpStatus.CREATED));
  }

  @PatchMapping("/{id}/price")
  public Mono<ResponseEntity<ItemDto>> changePrice(@PathVariable Long id, @RequestParam Long price) {
    return itemService.changePrice(id, price)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteItem(@PathVariable Long id) {
    return itemService.deleteItem(id)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}
