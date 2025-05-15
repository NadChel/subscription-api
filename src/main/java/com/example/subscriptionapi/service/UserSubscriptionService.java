package com.example.subscriptionapi.service;

import com.example.subscriptionapi.data.dto.response.SubscriptionResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserSubscriptionService {

    List<SubscriptionResponseDto> findSubscriptionsByUser(UUID userId);

    List<SubscriptionResponseDto> findTopSubscriptions();

    void save(UUID userId, UUID subId);

    void deleteByUserIdAndSubId(UUID userId, UUID subId);
}
