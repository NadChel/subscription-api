package com.example.productapi.mapper;

import com.example.productapi.data.dto.NewUserRequestDto;
import com.example.productapi.data.dto.UserResponseDto;
import com.example.productapi.data.dto.UpdatedUserRequestDto;
import com.example.productapi.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toUser(NewUserRequestDto dto);

    User toUser(UpdatedUserRequestDto dto);

    UserResponseDto toResponseDto(User user);
}
