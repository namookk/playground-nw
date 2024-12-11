package com.namookk.mvc.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  @Setter
  private Long id;

  private String name;

  private Long price;

  private static void validateName(String name) {
    Assert.hasText(name, "상품명은 필수값입니다.");
  }

  private static void validatePrice(Long price) {
    Assert.notNull(price, "상품가격은 필수값입니다.");
    Assert.isTrue(price > 0L, "상품가격이 올바르지 않습니다.");
  }

  public static Item createInstance(String name, Long price) {
    validateName(name);
    validatePrice(price);

    return Item.builder()
        .name(name)
        .price(price)
        .build();
  }

  public void update(Item item) {
    validateName(name);
    validatePrice(price);

    this.name = item.getName();
    this.price = item.getPrice();
  }

  public void changePrice(Long price) {
    validatePrice(price);

    this.price = price;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Item item = (Item) obj;
    return id.equals(item.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
