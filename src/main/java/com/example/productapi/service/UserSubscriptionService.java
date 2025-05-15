package com.example.productapi.service;

import com.example.productapi.data.dto.SubscriptionResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserSubscriptionService {

    List<SubscriptionResponseDto> findSubscriptionsByUser(UUID userId);

    List<SubscriptionResponseDto> findTopSubscriptions();

    void save(UUID userId, UUID subId);

    void deleteByUserIdAndSubId(UUID userId, UUID subId);
}
