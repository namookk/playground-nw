package com.namookk.mvc.controller;

import com.namookk.mvc.model.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @GetMapping("/me")
  public ResponseEntity<UserDto> getMyinfo() {
    UserDto userDto = UserDto.builder()
        .id(1L)
        .name("황남욱")
        .email("test@naver.com")
        .build();

    return ResponseEntity.ok(userDto);
  }
}
