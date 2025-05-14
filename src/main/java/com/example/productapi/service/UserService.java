package com.example.productapi.service;

import com.example.productapi.data.dto.NewUserRequestDto;
import com.example.productapi.data.dto.UpdatedUserRequestDto;
import com.example.productapi.data.dto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface UserService {

    UserResponseDto save(NewUserRequestDto dto);

    Optional<UserResponseDto> findById(UUID id);

    UserResponseDto update(UpdatedUserRequestDto dto);

    void deleteById(UUID id);
}
