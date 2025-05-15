package com.example.subscriptionapi.mapper;

import com.example.subscriptionapi.data.dto.request.NewSubscriptionRequestDto;
import com.example.subscriptionapi.data.dto.response.SubscriptionResponseDto;
import com.example.subscriptionapi.data.dto.request.UpdatedSubscriptionRequestDto;
import com.example.subscriptionapi.data.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionMapper {

    Subscription toSubscription(NewSubscriptionRequestDto dto);

    Subscription toSubscription(UpdatedSubscriptionRequestDto dto);

    SubscriptionResponseDto toResponseDto(Subscription subscription);

    List<SubscriptionResponseDto> toResponseDtos(List<Subscription> subscriptions);
}
