package com.namookk.webflux.service;

import com.namookk.webflux.config.exception.DataNotFoundException;
import com.namookk.webflux.entity.Item;
import com.namookk.webflux.model.dto.CreateItemDto;
import com.namookk.webflux.model.dto.ItemDto;
import com.namookk.webflux.repository.ItemList;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemService {

  private final ItemList itemList = ItemList.getInstance();

  // 전체 아이템 가져오기
  public Flux<ItemDto> getItems() {
    return itemList.getItems().map(ItemDto::from);
  }

  // 특정 아이템 정보 가져오기
  public Mono<ItemDto> getItemInfo(Long id) {
    return itemList.findById(id)
        .map(ItemDto::from)
        .switchIfEmpty(Mono.error(new DataNotFoundException("%s 는 없는 아이템입니다.".formatted(id))));
  }

  // 아이템 저장하기
  public Mono<ItemDto> saveItem(CreateItemDto request) {
    Item newItem = Item.createInstance(request.getName(), request.getPrice());
    return itemList.saveItem(newItem).map(ItemDto::from);
  }

  // 아이템 가격 변경
  public Mono<ItemDto> changePrice(Long id, Long price) {
    return itemList.findById(id)
        .doOnNext(item -> item.changePrice(price))
        .map(ItemDto::from)
        .switchIfEmpty(Mono.error(new DataNotFoundException("%s 는 없는 아이템입니다.".formatted(id))));
  }

  // 아이템 삭제
  public Mono<Void> deleteItem(Long id) {
    return itemList.findById(id)
        .flatMap(itemList::removeItem)
        .switchIfEmpty(Mono.error(new DataNotFoundException("%s 는 없는 아이템입니다.".formatted(id))));
  }
}
