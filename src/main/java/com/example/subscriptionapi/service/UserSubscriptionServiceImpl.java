package com.example.subscriptionapi.service;

import com.example.subscriptionapi.data.dto.response.SubscriptionResponseDto;
import com.example.subscriptionapi.data.entity.Subscription;
import com.example.subscriptionapi.data.entity.User;
import com.example.subscriptionapi.mapper.SubscriptionMapper;
import com.example.subscriptionapi.repository.SubscriptionRepository;
import com.example.subscriptionapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        List<SubscriptionResponseDto> subscriptionDtos = subscriptionMapper.toResponseDtos(subscriptions);
        return subscriptionDtos;
    }

    private User loadUser(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) throw new EntityNotFoundException("No such user");
        User user = userOptional.get();
        return user;
    }

    @Override
    public List<SubscriptionResponseDto> findTopSubscriptions() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Subscription> subscriptions = subscriptionRepository.findTopSubscriptions(pageable);
        List<SubscriptionResponseDto> subscriptionDtos = subscriptionMapper.toResponseDtos(subscriptions);
        return subscriptionDtos;
    }

    @Override
    @Transactional(readOnly = false)
    public void save(UUID userId, UUID subId) {
        User user = loadUser(userId);
        Subscription subscription = loadSubscription(subId);
        List<Subscription> userSubscriptions = user.getSubscriptions();
        userSubscriptions.add(subscription);
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
        User user = loadUser(userId);
        Subscription subscription = loadSubscription(subId);
        List<Subscription> userSubscriptions = user.getSubscriptions();
        userSubscriptions.remove(subscription);
    }
}
