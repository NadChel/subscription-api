package com.example.productapi.service;

import com.example.productapi.data.dto.SubscriptionResponseDto;
import com.example.productapi.data.entity.Subscription;
import com.example.productapi.data.entity.User;
import com.example.productapi.mapper.SubscriptionMapper;
import com.example.productapi.repository.SubscriptionRepository;
import com.example.productapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public List<SubscriptionResponseDto> findSubscriptionsByUser(UUID userId) {
        User savedUser = loadUser(userId);
        List<Subscription> subscriptions = savedUser.getSubscriptions();
        List<SubscriptionResponseDto> existingSubscriptionDtos = subscriptionMapper.toResponseDtos(subscriptions);
        return existingSubscriptionDtos;
    }

    @Override
    public List<SubscriptionResponseDto> findTopSubscriptions() {
        PageRequest pageable = PageRequest.of(0, 3);
        List<Subscription> subscriptions = subscriptionRepository.findTopSubscriptions(pageable);
        List<SubscriptionResponseDto> subscriptionDtos = subscriptionMapper.toResponseDtos(subscriptions);
        return subscriptionDtos;
    }

    private User loadUser(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) throw new EntityNotFoundException("No such user");
        User savedUser = userOptional.get();
        return savedUser;
    }

    @Override
    @Transactional(readOnly = false)
    public void save(UUID userId, UUID subId) {
        User savedUser = loadUser(userId);
        Subscription subscription = loadSubscription(subId);
        List<Subscription> existingSubscriptions = savedUser.getSubscriptions();
        existingSubscriptions.add(subscription);
    }

    private Subscription loadSubscription(UUID subId) {
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(subId);
        if (subscriptionOptional.isEmpty()) throw new EntityNotFoundException("No such subscription");
        Subscription subscription = subscriptionOptional.get();
        return subscription;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteByUserIdAndSubId(UUID userId, UUID subId) {
        User savedUser = loadUser(userId);
        Subscription savedSubscription = loadSubscription(subId);
        List<Subscription> existingSubscriptions = savedUser.getSubscriptions();
        existingSubscriptions.remove(savedSubscription);
    }
}
