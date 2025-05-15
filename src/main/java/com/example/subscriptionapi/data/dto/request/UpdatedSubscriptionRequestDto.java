package com.example.subscriptionapi.data.dto.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class UpdatedSubscriptionRequestDto {
    private UUID id;
    private String name;
}
