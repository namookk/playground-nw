package com.namookk.mvc.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateItemDto {
  String name;
  Long price;
}
