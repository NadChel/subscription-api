package com.example.productapi.controller;

import com.example.productapi.data.dto.NewSubscriptionRequestDto;
import com.example.productapi.data.dto.SubscriptionResponseDto;
import com.example.productapi.data.dto.UpdatedSubscriptionRequestDto;
import com.example.productapi.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService service;

    @PostMapping
    public ResponseEntity<SubscriptionResponseDto> saveSubscription(@RequestBody NewSubscriptionRequestDto dto) {
        SubscriptionResponseDto savedUserDto = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
    }

    @PutMapping
    public ResponseEntity<SubscriptionResponseDto> updateSubscription(@RequestBody UpdatedSubscriptionRequestDto dto) {
        SubscriptionResponseDto savedSubscriptionDto = service.update(dto);
        return ResponseEntity.ok(savedSubscriptionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
