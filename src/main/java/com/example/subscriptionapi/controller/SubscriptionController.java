package com.example.subscriptionapi.controller;

import com.example.subscriptionapi.data.dto.request.NewSubscriptionRequestDto;
import com.example.subscriptionapi.data.dto.response.SubscriptionResponseDto;
import com.example.subscriptionapi.data.dto.request.UpdatedSubscriptionRequestDto;
import com.example.subscriptionapi.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SubscriptionController {

    private final SubscriptionService service;

    @PostMapping
    public ResponseEntity<SubscriptionResponseDto> saveSubscription(@RequestBody NewSubscriptionRequestDto dto) {
        log.info("POST /subscriptions");
        SubscriptionResponseDto savedUserDto = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
    }

    @PutMapping
    public ResponseEntity<SubscriptionResponseDto> updateSubscription(@RequestBody UpdatedSubscriptionRequestDto dto) {
        log.info("PUT /subscriptions");
        SubscriptionResponseDto savedSubscriptionDto = service.update(dto);
        return ResponseEntity.ok(savedSubscriptionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable("id") UUID id) {
        log.info("DELETE /subscriptions/{id}");
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
