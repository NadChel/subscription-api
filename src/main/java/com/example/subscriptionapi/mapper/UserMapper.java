package com.example.subscriptionapi.mapper;

import com.example.subscriptionapi.data.dto.request.NewUserRequestDto;
import com.example.subscriptionapi.data.dto.response.UserResponseDto;
import com.example.subscriptionapi.data.dto.request.UpdatedUserRequestDto;
import com.example.subscriptionapi.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUser(NewUserRequestDto dto);

    User toUser(UpdatedUserRequestDto dto);

    UserResponseDto toResponseDto(User user);
}
