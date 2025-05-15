package com.example.productapi.controller;

import com.example.productapi.data.dto.SubscriptionResponseDto;
import com.example.productapi.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users-subscriptions")
@RequiredArgsConstructor
public class UserSubscriptionController {

    private final UserSubscriptionService service;

    @GetMapping("/{user-id}")
    public ResponseEntity<List<SubscriptionResponseDto>> findSubscriptionsByUser(@PathVariable("user-id") UUID userId) {
        List<SubscriptionResponseDto> subscriptions = service.findSubscriptionsByUser(userId);
        return ResponseEntity.ok().body(subscriptions);
    }

    @GetMapping("/top")
    public ResponseEntity<List<SubscriptionResponseDto>> findTopSubscriptions() {
        List<SubscriptionResponseDto> subscriptions = service.findTopSubscriptions();
        return ResponseEntity.ok().body(subscriptions);
    }

    @PostMapping("/{user-id}/{sub-id}")
    public ResponseEntity<Void> saveUserSubscription(@PathVariable("user-id") UUID userId, @PathVariable("sub-id") UUID subId) {
        service.save(userId, subId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{user-id}/{sub-id}")
    public ResponseEntity<Void> deleteUserSubscription(@PathVariable("user-id") UUID userId, @PathVariable("sub-id") UUID subId) {
        service.deleteByUserIdAndSubId(userId, subId);
        return ResponseEntity.ok().build();
    }
}
