package com.example.subscriptionapi.controller;

import com.example.subscriptionapi.data.dto.request.NewUserRequestDto;
import com.example.subscriptionapi.data.dto.request.UpdatedUserRequestDto;
import com.example.subscriptionapi.data.dto.response.UserResponseDto;
import com.example.subscriptionapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserService userServiceMock;

    @Test
    void getUser_ifUserExists_returns200_withRequestedUser() throws Exception {
        UUID id = UUID.fromString("5bc10973-05c9-41a4-a0b2-c04320e95651");

        UserResponseDto expectedUserResponseDto = new UserResponseDto();
        expectedUserResponseDto.setUsername("Maxine");

        given(userServiceMock.findById(id)).willReturn(Optional.of(expectedUserResponseDto));

        MockHttpServletResponse response = mockMvc.perform(get("/users/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        UserResponseDto actualUserResponseDto =
                objectMapper.readValue(response.getContentAsString(), UserResponseDto.class);
        assertThat(expectedUserResponseDto).isEqualTo(actualUserResponseDto);
    }

    @Test
    void getUser_ifUserDoesNotExist_returns4xx_withNonBlankDetailMessage() throws Exception {
        UUID id = UUID.fromString("5bc10973-05c9-41a4-a0b2-c04320e95651");

        given(userServiceMock.findById(id)).willReturn(Optional.empty());

        MockHttpServletResponse response = mockMvc.perform(get("/users/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse();

        ProblemDetail problemDetail =
                objectMapper.readValue(response.getContentAsString(), ProblemDetail.class);
        assertThat(problemDetail.getDetail()).isNotBlank();
    }

    @Test
    void saveUser_returns201_withUserDtoReturnedByService() throws Exception {
        NewUserRequestDto newUserRequestDto = new NewUserRequestDto();
        newUserRequestDto.setUsername("Sasha");
        UserResponseDto expectedUserResponseDto = new UserResponseDto();
        expectedUserResponseDto.setUsername(newUserRequestDto.getUsername());

        given(userServiceMock.save(newUserRequestDto)).willReturn(expectedUserResponseDto);

        MockHttpServletResponse response = mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(newUserRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        UserResponseDto actualUserResponseDto =
                objectMapper.readValue(response.getContentAsString(), UserResponseDto.class);
        assertThat(expectedUserResponseDto).isEqualTo(actualUserResponseDto);

        ArgumentCaptor<NewUserRequestDto> captor = ArgumentCaptor.forClass(NewUserRequestDto.class);
        then(userServiceMock).should().save(captor.capture());
        assertThat(newUserRequestDto).isEqualTo(captor.getValue());
    }

    @Test
    void updateUser_returns200_withUserReturnedByService() throws Exception {
        UpdatedUserRequestDto updatedUserRequestDto = new UpdatedUserRequestDto();
        UUID id = UUID.fromString("5bc10973-05c9-41a4-a0b2-c04320e95651");
        updatedUserRequestDto.setId(id);
        updatedUserRequestDto.setUsername("Masha");
        UserResponseDto expectedUserResponseDto = new UserResponseDto();
        expectedUserResponseDto.setId(updatedUserRequestDto.getId());
        expectedUserResponseDto.setUsername(updatedUserRequestDto.getUsername());

        given(userServiceMock.update(updatedUserRequestDto)).willReturn(expectedUserResponseDto);

        MockHttpServletResponse response = mockMvc.perform(put("/users")
                        .content(objectMapper.writeValueAsString(updatedUserRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        UserResponseDto actualUserResponseDto =
                objectMapper.readValue(response.getContentAsString(), UserResponseDto.class);
        assertThat(expectedUserResponseDto).isEqualTo(actualUserResponseDto);

        ArgumentCaptor<UpdatedUserRequestDto> captor = ArgumentCaptor.forClass(UpdatedUserRequestDto.class);
        then(userServiceMock).should().update(captor.capture());
        assertThat(updatedUserRequestDto).isEqualTo(captor.getValue());
    }

    @Test
    void deleteUser_invokesDeleteOnServiceWithPassedId_andReturns200() throws Exception {
        UUID id = UUID.fromString("5bc10973-05c9-41a4-a0b2-c04320e95651");

        willDoNothing().given(userServiceMock).deleteById(id);

        mockMvc.perform(delete("/users/" + id))
                .andExpect(status().isOk());

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);
        then(userServiceMock).should().deleteById(captor.capture());
        assertThat(id).isEqualTo(captor.getValue());
    }
}