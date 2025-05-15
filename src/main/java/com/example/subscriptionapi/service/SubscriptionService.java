package com.example.subscriptionapi.service;

import com.example.subscriptionapi.data.dto.request.NewSubscriptionRequestDto;
import com.example.subscriptionapi.data.dto.response.SubscriptionResponseDto;
import com.example.subscriptionapi.data.dto.request.UpdatedSubscriptionRequestDto;

import java.util.UUID;

public interface SubscriptionService {

    SubscriptionResponseDto save(NewSubscriptionRequestDto dto);

    SubscriptionResponseDto update(UpdatedSubscriptionRequestDto dto);

    void deleteById(UUID id);
}
