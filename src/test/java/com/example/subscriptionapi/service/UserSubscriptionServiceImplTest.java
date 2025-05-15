package com.example.subscriptionapi.service;

import com.example.subscriptionapi.data.dto.response.SubscriptionResponseDto;
import com.example.subscriptionapi.data.entity.Subscription;
import com.example.subscriptionapi.data.entity.User;
import com.example.subscriptionapi.mapper.SubscriptionMapper;
import com.example.subscriptionapi.repository.SubscriptionRepository;
import com.example.subscriptionapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserSubscriptionServiceImplTest {

    @Mock
    UserRepository userRepositoryMock;
    @Mock
    SubscriptionRepository subscriptionRepositoryMock;
    @Mock
    SubscriptionMapper subscriptionMapperMock;

    @Test
    void findSubscriptionsByUser_ifUserDoesNotExist_throwsEntityNotFoundException() {
        UUID id = UUID.fromString("5bc10973-05c9-41a4-a0b2-c04320e95651");

        given(userRepositoryMock.findById(id)).willReturn(Optional.empty());

        UserSubscriptionServiceImpl userSubscriptionService =
                new UserSubscriptionServiceImpl(userRepositoryMock, subscriptionRepositoryMock, subscriptionMapperMock);
        assertThatThrownBy(() -> userSubscriptionService.findSubscriptionsByUser(id)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void findSubscriptionsByUser_ifUserExists_returnsUserSubscriptions() {
        Subscription subscription = new Subscription();
        subscription.setName("Netflix");

        SubscriptionResponseDto subscriptionDto = new SubscriptionResponseDto();
        subscriptionDto.setName(subscription.getName());

        List<Subscription> subscriptions = List.of(subscription);
        List<SubscriptionResponseDto> expectedUserSubscriptionDtos = List.of(subscriptionDto);

        UUID id = UUID.fromString("5bc10973-05c9-41a4-a0b2-c04320e95651");

        User user = new User();
        user.setId(id);
        user.setUsername("Alex");
        user.setSubscriptions(subscriptions);

        given(userRepositoryMock.findById(id)).willReturn(Optional.of(user));
        given(subscriptionMapperMock.toResponseDtos(subscriptions)).willReturn(expectedUserSubscriptionDtos);

        UserSubscriptionServiceImpl userSubscriptionService =
                new UserSubscriptionServiceImpl(userRepositoryMock, subscriptionRepositoryMock, subscriptionMapperMock);
        List<SubscriptionResponseDto> actualUserSubscriptionDtos = userSubscriptionService.findSubscriptionsByUser(id);
        assertThat(actualUserSubscriptionDtos).containsExactlyInAnyOrderElementsOf(expectedUserSubscriptionDtos);
    }

    @Test
    void save_ifUserExists_andSubscriptionExists_addsSubscriptionToUser() {
        UUID subId = UUID.fromString("a3aa8dfa-0b2c-4e9e-a745-b64d0c488d66");

        Subscription subscription = new Subscription();
        subscription.setId(subId);
        subscription.setName("Netflix");

        UUID userId = UUID.fromString("5bc10973-05c9-41a4-a0b2-c04320e95651");

        User user = new User();
        user.setId(userId);
        user.setUsername("Alex");
        user.setSubscriptions(new ArrayList<>());

        given(userRepositoryMock.findById(userId)).willReturn(Optional.of(user));
        given(subscriptionRepositoryMock.findById(subId)).willReturn(Optional.of(subscription));

        assumeThat(user.getSubscriptions()).isEmpty();

        UserSubscriptionServiceImpl userSubscriptionService =
                new UserSubscriptionServiceImpl(userRepositoryMock, subscriptionRepositoryMock, subscriptionMapperMock);
        userSubscriptionService.save(userId, subId);

        assertThat(user.getSubscriptions()).containsExactly(subscription);
    }
}