package com.example.subscriptionapi.controller;

import com.example.subscriptionapi.data.dto.request.NewUserRequestDto;
import com.example.subscriptionapi.data.dto.request.UpdatedUserRequestDto;
import com.example.subscriptionapi.data.dto.response.UserResponseDto;
import com.example.subscriptionapi.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody NewUserRequestDto dto) {
        log.info("POST /users");
        UserResponseDto savedUserDto = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable UUID id) {
        log.info("GET /users/{id}");
        UserResponseDto userDto = service.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No such user"));
        return ResponseEntity.ok(userDto);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UpdatedUserRequestDto dto) {
        log.info("PUT /users");
        UserResponseDto updatedUserDto = service.update(dto);
        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        log.info("DELETE /users/{id}");
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
