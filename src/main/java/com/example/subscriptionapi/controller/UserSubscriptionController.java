package com.example.subscriptionapi.controller;

import com.example.subscriptionapi.data.dto.response.SubscriptionResponseDto;
import com.example.subscriptionapi.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserSubscriptionController {

    private final UserSubscriptionService service;

    @GetMapping("/{user-id}")
    public ResponseEntity<List<SubscriptionResponseDto>> findSubscriptionsByUser(@PathVariable("user-id") UUID userId) {
        log.info("GET /user-subscriptions/{user-id}");
        List<SubscriptionResponseDto> subscriptions = service.findSubscriptionsByUser(userId);
        return ResponseEntity.ok().body(subscriptions);
    }

    @GetMapping("/top")
    public ResponseEntity<List<SubscriptionResponseDto>> findTopSubscriptions() {
        log.info("GET /user-subscriptions/top");
        List<SubscriptionResponseDto> subscriptions = service.findTopSubscriptions();
        return ResponseEntity.ok().body(subscriptions);
    }

    @PostMapping("/{user-id}/{sub-id}")
    public ResponseEntity<Void> saveUserSubscription(@PathVariable("user-id") UUID userId, @PathVariable("sub-id") UUID subId) {
        log.info("POST /user-subscriptions/{user-id}/{sub-id}");
        service.save(userId, subId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{user-id}/{sub-id}")
    public ResponseEntity<Void> deleteUserSubscription(@PathVariable("user-id") UUID userId, @PathVariable("sub-id") UUID subId) {
        log.info("DELETE /user-subscriptions/{user-id}/{sub-id}");
        service.deleteByUserIdAndSubId(userId, subId);
        return ResponseEntity.ok().build();
    }
}
