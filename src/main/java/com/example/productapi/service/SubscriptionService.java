package com.example.productapi.service;

import com.example.productapi.data.dto.NewSubscriptionRequestDto;
import com.example.productapi.data.dto.SubscriptionResponseDto;
import com.example.productapi.data.dto.UpdatedSubscriptionRequestDto;

import java.util.UUID;

public interface SubscriptionService {

    SubscriptionResponseDto save(NewSubscriptionRequestDto dto);

    SubscriptionResponseDto update(UpdatedSubscriptionRequestDto dto);

    void deleteById(UUID id);
}
