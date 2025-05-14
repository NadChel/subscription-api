package com.example.productapi.mapper;

import com.example.productapi.data.dto.NewSubscriptionRequestDto;
import com.example.productapi.data.dto.SubscriptionResponseDto;
import com.example.productapi.data.dto.UpdatedSubscriptionRequestDto;
import com.example.productapi.data.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionMapper {
    Subscription toSubscription(NewSubscriptionRequestDto dto);

    Subscription toSubscription(UpdatedSubscriptionRequestDto dto);

    SubscriptionResponseDto toResponseDto(Subscription subscription);
}
