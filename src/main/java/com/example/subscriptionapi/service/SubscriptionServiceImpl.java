package com.example.subscriptionapi.service;

import com.example.subscriptionapi.data.dto.request.NewSubscriptionRequestDto;
import com.example.subscriptionapi.data.dto.response.SubscriptionResponseDto;
import com.example.subscriptionapi.data.dto.request.UpdatedSubscriptionRequestDto;
import com.example.subscriptionapi.data.entity.Subscription;
import com.example.subscriptionapi.mapper.SubscriptionMapper;
import com.example.subscriptionapi.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository repository;
    private final SubscriptionMapper mapper;

    @Override
    @Transactional(readOnly = false)
    public SubscriptionResponseDto save(NewSubscriptionRequestDto dto) {
        Subscription subscription = mapper.toSubscription(dto);
        Subscription savedSubscription = repository.save(subscription);
        return mapper.toResponseDto(savedSubscription);
    }

    @Override
    @Transactional(readOnly = false)
    public SubscriptionResponseDto update(UpdatedSubscriptionRequestDto dto) {
        Subscription subscription = mapper.toSubscription(dto);
        Subscription updatedSubscription = repository.save(subscription);
        return mapper.toResponseDto(updatedSubscription);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
