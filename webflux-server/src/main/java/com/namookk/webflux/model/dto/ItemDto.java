package com.namookk.webflux.model.dto;

import com.namookk.webflux.entity.Item;
import lombok.Value;

@Value
public class ItemDto {
  Long id;
  String name;
  Long price;

  public static ItemDto from(Item item){
    return new ItemDto(item.getId(), item.getName(), item.getPrice());
  }
}
