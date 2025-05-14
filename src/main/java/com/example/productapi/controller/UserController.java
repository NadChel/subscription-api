package com.example.productapi.controller;

import com.example.productapi.data.dto.NewUserRequestDto;
import com.example.productapi.data.dto.UpdatedUserRequestDto;
import com.example.productapi.data.dto.UserResponseDto;
import com.example.productapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody NewUserRequestDto dto) {
        UserResponseDto savedUserDto = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable UUID id) {
        UserResponseDto userDto = service.findById(id).orElse(null);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UpdatedUserRequestDto dto) {
        UserResponseDto savedProductDto = service.update(dto);
        return ResponseEntity.ok(savedProductDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
