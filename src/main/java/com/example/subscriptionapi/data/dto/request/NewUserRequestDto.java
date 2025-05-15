package com.example.subscriptionapi.data.dto.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class NewUserRequestDto {
    private String username;
}
