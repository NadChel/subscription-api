package com.example.productapi.data.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class UpdatedUserRequestDto {
    private UUID id;
    private String username;
}
