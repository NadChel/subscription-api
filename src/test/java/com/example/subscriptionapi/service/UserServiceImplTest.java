package com.example.subscriptionapi.service;

import com.example.subscriptionapi.data.dto.request.NewUserRequestDto;
import com.example.subscriptionapi.data.dto.request.UpdatedUserRequestDto;
import com.example.subscriptionapi.data.dto.response.UserResponseDto;
import com.example.subscriptionapi.data.entity.User;
import com.example.subscriptionapi.mapper.UserMapper;
import com.example.subscriptionapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepositoryMock;
    @Mock
    UserMapper userMapperMock;

    @Test
    void findById_ifUserWithPassedInIdFound_returnsOptionalOfFoundUser() {
        UUID id = UUID.fromString("5bc10973-05c9-41a4-a0b2-c04320e95651");
        User user = new User();
        user.setId(id);
        UserResponseDto expectedUserResponseDto = new UserResponseDto();
        expectedUserResponseDto.setId(user.getId());

        given(userRepositoryMock.findById(id)).willReturn(Optional.of(user));
        given(userMapperMock.toResponseDto(user)).willReturn(expectedUserResponseDto);

        UserService userService = new UserServiceImpl(userRepositoryMock, userMapperMock);
        Optional<UserResponseDto> actualUserResponseDtoOptional = userService.findById(id);

        assertThat(actualUserResponseDtoOptional).isPresent();
        assertThat(actualUserResponseDtoOptional.get()).isEqualTo(expectedUserResponseDto);
    }

    @Test
    void save_ifPassedDtoNotNull_returnsSavedUserDto() {
        NewUserRequestDto newUserRequestDto = new NewUserRequestDto();
        User user = new User();
        User savedUser = new User();
        UserResponseDto expectedUserResponseDto = new UserResponseDto();
        UUID id = UUID.fromString("5bc10973-05c9-41a4-a0b2-c04320e95651");
        savedUser.setId(id);
        expectedUserResponseDto.setId(savedUser.getId());

        given(userMapperMock.toUser(newUserRequestDto)).willReturn(user);
        given(userMapperMock.toResponseDto(savedUser)).willReturn(expectedUserResponseDto);
        given(userRepositoryMock.save(user)).willReturn(savedUser);

        UserService userService = new UserServiceImpl(userRepositoryMock, userMapperMock);
        UserResponseDto actualUserResponseDto = userService.save(newUserRequestDto);

        assertThat(actualUserResponseDto).isNotNull();
        assertThat(actualUserResponseDto).isEqualTo(expectedUserResponseDto);
    }

    @Test
    void update_ifPassedDtoNotNull_returnsSavedUserDto() {
        UpdatedUserRequestDto updatedUserRequestDto = new UpdatedUserRequestDto();
        UUID id = UUID.fromString("5bc10973-05c9-41a4-a0b2-c04320e95651");
        updatedUserRequestDto.setId(id);
        User user = new User();
        User updatedUser = new User();
        updatedUser.setId(updatedUserRequestDto.getId());
        UserResponseDto expectedUserResponseDto = new UserResponseDto();
        expectedUserResponseDto.setId(updatedUser.getId());
        expectedUserResponseDto.setUsername(updatedUser.getUsername());

        given(userMapperMock.toUser(updatedUserRequestDto)).willReturn(user);
        given(userMapperMock.toResponseDto(updatedUser)).willReturn(expectedUserResponseDto);
        given(userRepositoryMock.save(user)).willReturn(updatedUser);

        UserService userService = new UserServiceImpl(userRepositoryMock, userMapperMock);
        UserResponseDto actualUserResponseDto = userService.update(updatedUserRequestDto);

        assertThat(actualUserResponseDto).isEqualTo(expectedUserResponseDto);
    }

    @Test
    void deleteById_forwardsToRepositoryWithPassedId() {
        UUID actualId = UUID.fromString("5bc10973-05c9-41a4-a0b2-c04320e95651");

        UserService userService = new UserServiceImpl(userRepositoryMock, null);
        userService.deleteById(actualId);

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);
        then(userRepositoryMock).should().deleteById(captor.capture());
        UUID expectedId = captor.getValue();
        assertThat(actualId).isEqualTo(expectedId);
    }
}