package com.example.subscriptionapi.service;

import com.example.subscriptionapi.data.dto.request.NewUserRequestDto;
import com.example.subscriptionapi.data.dto.request.UpdatedUserRequestDto;
import com.example.subscriptionapi.data.dto.response.UserResponseDto;
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
