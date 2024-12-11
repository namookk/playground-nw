package com.namookk.mvc.service;

import com.namookk.mvc.config.exception.DataNotFoundException;
import com.namookk.mvc.model.dto.CreateItemDto;
import com.namookk.mvc.entity.Item;
import com.namookk.mvc.model.dto.ItemDto;
import com.namookk.mvc.repository.ItemList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

  public List<ItemDto> getItems() {
    List<Item> items = ItemList.getInstance().getItems();
    return items.stream()
        .map(ItemDto::from)
        .toList();
  }

  public ItemDto getItemInfo(Long id) {
    Item item = getItemById(id);
    return ItemDto.from(item);
  }

  public ItemDto saveItem(CreateItemDto request) {
    Item newItem = Item.createInstance(request.getName(), request.getPrice());
    Item savedItem = ItemList.getInstance().saveItem(newItem);
    return ItemDto.from(savedItem);
  }

  public ItemDto changePrice(Long id, Long price) {
    Item item = getItemById(id);
    item.changePrice(price);
    return ItemDto.from(item);
  }

  public void deleteItem(Long id) {
    Item item = getItemById(id);
    ItemList.getInstance().removeItem(item);
  }

  private Item getItemById(Long id) {
    return ItemList.getInstance().findById(id)
        .orElseThrow(() -> new DataNotFoundException("%s 는 없는 아이템입니다.".formatted(id)));
  }
}
