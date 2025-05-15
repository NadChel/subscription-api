package com.example.subscriptionapi.service;

import com.example.subscriptionapi.data.dto.request.NewUserRequestDto;
import com.example.subscriptionapi.data.dto.request.UpdatedUserRequestDto;
import com.example.subscriptionapi.data.dto.response.UserResponseDto;
import com.example.subscriptionapi.data.entity.User;
import com.example.subscriptionapi.mapper.UserMapper;
import com.example.subscriptionapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional(readOnly = false)
    public UserResponseDto save(NewUserRequestDto dto) {
        User user = mapper.toUser(dto);
        User savedUser = repository.save(user);
        return mapper.toResponseDto(savedUser);
    }

    @Override
    public Optional<UserResponseDto> findById(UUID id) {
        Optional<User> userOptional = repository.findById(id);
        return userOptional.map(mapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = false)
    public UserResponseDto update(UpdatedUserRequestDto dto) {
        User user = mapper.toUser(dto);
        User updatedUser = repository.save(user);
        return mapper.toResponseDto(updatedUser);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
