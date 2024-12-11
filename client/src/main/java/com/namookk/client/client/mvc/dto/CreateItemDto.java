package com.namookk.client.client.mvc.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateItemDto {
  String name;
  Long price;
}
