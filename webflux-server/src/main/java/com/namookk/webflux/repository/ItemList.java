package com.namookk.webflux.repository;

import com.namookk.webflux.entity.Item;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ItemList {

  private final Map<Long, Item> items = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong();

  // 초기화 블록을 통해 기본 데이터 설정
  private ItemList() {
    saveItem(new Item(null, "후레시베리", 2000L)).subscribe();
    saveItem(new Item(null, "초코하임", 2300L)).subscribe();
    saveItem(new Item(null, "쌀과자", 500L)).subscribe();
  }

  // 싱글톤 패턴을 위한 정적 내부 클래스
  private static class ItemListHolder {
    private static final ItemList ITEM_LIST_INSTANCE = new ItemList();
  }

  // 싱글톤 인스턴스 반환
  public static ItemList getInstance() {
    return ItemListHolder.ITEM_LIST_INSTANCE;
  }

  // 모든 아이템을 Flux로 반환
  public Flux<Item> getItems() {
    return Flux.fromIterable(items.values());
  }

  // 아이템 저장 메서드 (업데이트 or 추가)
  public Mono<Item> saveItem(final Item item) {
    if (item.getId() == null) {
      item.setId(idCounter.incrementAndGet());
    }
    items.merge(item.getId(), item, (existingItem, newItem) -> {
      existingItem.update(newItem);
      return existingItem;
    });
    return Mono.just(items.get(item.getId()));
  }

  // ID를 기준으로 아이템 삭제
  public Mono<Void> removeById(Long itemId) {
    items.remove(itemId);
    return Mono.empty();
  }

  // 아이템 삭제
  public Mono<Void> removeItem(Item item) {
    return removeById(item.getId());
  }

  // ID로 아이템 찾기
  public Mono<Item> findById(Long itemId) {
    return Mono.justOrEmpty(items.get(itemId));
  }
}
