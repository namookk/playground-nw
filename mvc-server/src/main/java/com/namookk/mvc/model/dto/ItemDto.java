package com.namookk.mvc.model.dto;

import com.namookk.mvc.entity.Item;
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
