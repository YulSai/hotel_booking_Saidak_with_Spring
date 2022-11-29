package com.company.hotel_booking.web.controllers.rest;

import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.utils.DtoTest;
import com.company.hotel_booking.utils.TestConstants;
import com.company.hotel_booking.utils.exceptions.users.UserAlreadyExistsException;
import com.company.hotel_booking.utils.exceptions.users.UserDeleteException;
import com.company.hotel_booking.utils.exceptions.users.UserNotFoundException;
import com.company.hotel_booking.web.controllers.utils.PagingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class with tests for UserRestController
 */
@WebMvcTest(controllers = UserRestController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private PagingUtil pagingUtil;
    private UserDto userDto;
    private MockMultipartFile imageFile;

    @BeforeEach
    public void setup() {
        userDto = DtoTest.getExpectedUserWithId();
        imageFile = new MockMultipartFile("avatarFile", "avatar_test.png",
                "text/plain", "some xml".getBytes());
    }

    @Test
    void whenRequestAllUsers_thenReturnFoundUserEntries() throws Exception {
        List<UserDto> users = Collections.singletonList(userDto);
        Page<UserDto> pageUserDto = new PageImpl<>(new ArrayList<>(users));

        when(userService.findAllPages(any())).thenReturn(pageUserDto);
        this.mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("content", hasSize(1)),
                        jsonPath("content[0].id").value(TestConstants.USER_ID),
                        jsonPath("content[0].username").value(TestConstants.USER_USERNAME),
                        jsonPath("content[0].password").value(TestConstants.USER_PASSWORD),
                        jsonPath("content[0].firstName").value(TestConstants.USER_FIRSTNAME),
                        jsonPath("content[0].lastName").value(TestConstants.USER_LASTNAME),
                        jsonPath("content[0].email").value(TestConstants.USER_EMAIL),
                        jsonPath("content[0].phoneNumber").value(TestConstants.USER_PHONE_NUMBER),
                        jsonPath("content[0].role").value(TestConstants.USER_ROLE_ADMIN),
                        jsonPath("content[0].avatar").value(TestConstants.USER_AVATAR),
                        jsonPath("content[0].block").value(TestConstants.USER_BLOCK)
                );

        verify(userService, times(1)).findAllPages(any());
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenRequestAllUsersEmpty_thenReturnFoundZeroUserEntries() throws Exception {
        Page<UserDto> pageUserDto = new PageImpl<>(new ArrayList<>());
        when(userService.findAllPages(any())).thenReturn(pageUserDto);
        this.mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("content", hasSize(0))
                );

        verify(userService, times(1)).findAllPages(any());
        verifyNoMoreInteractions(userService);
    }

    @Test
    @WithMockUser
    void whenRequestOneUserById_thenReturnFoundUserEntry() throws Exception {
        when(userService.findById(userDto.getId())).thenReturn(userDto);
        this.mockMvc.perform(
                        get("/api/users/{id}", userDto.getId())
                                .sessionAttr("user", userDto)
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(TestConstants.USER_ID),
                        jsonPath("$.username").value(TestConstants.USER_USERNAME),
                        jsonPath("$.password").value(TestConstants.USER_PASSWORD),
                        jsonPath("$.firstName").value(TestConstants.USER_FIRSTNAME),
                        jsonPath("$.lastName").value(TestConstants.USER_LASTNAME),
                        jsonPath("$.email").value(TestConstants.USER_EMAIL),
                        jsonPath("$.phoneNumber").value(TestConstants.USER_PHONE_NUMBER),
                        jsonPath("$.role").value(TestConstants.USER_ROLE_ADMIN),
                        jsonPath("$.avatar").value(TestConstants.USER_AVATAR),
                        jsonPath("$.block").value(TestConstants.USER_BLOCK)
                );

        verify(userService, times(1)).findById(userDto.getId());
        verifyNoMoreInteractions(userService);
    }

    @Test
    @WithMockUser
    void whenRequestNonExistingUser_thenReturnNotFound() throws Exception {
        when(userService.findById(userDto.getId())).thenThrow(UserNotFoundException.class);
        this.mockMvc.perform(get("/api/users/{id}", userDto.getId())
                        .sessionAttr("user", userDto)
                )
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findById(userDto.getId());
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenSendUserCreationFormCorrectData_thenReturnCreatedUserEntry() throws Exception {
        UserDto userCreated = DtoTest.getExpectedUserFormCreate();
        UserDto userCreatedWithId = DtoTest.getExpectedUserFormCreateWithId();

        when(userService.processCreateUser(refEq(userCreated), refEq(imageFile))).thenReturn(userCreatedWithId);
        this.mockMvc.perform(multipart("/api/users")
                        .file(imageFile)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DtoTest.convertObjectToJsonBytes(userCreated))
                )
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(userCreatedWithId.getId()),
                        jsonPath("$.username").value(userCreatedWithId.getUsername()),
                        jsonPath("$.password").value(userCreatedWithId.getPassword()),
                        jsonPath("$.firstName").value(userCreatedWithId.getFirstName()),
                        jsonPath("$.lastName").value(userCreatedWithId.getLastName()),
                        jsonPath("$.email").value(userCreatedWithId.getEmail()),
                        jsonPath("$.phoneNumber").value(userCreatedWithId.getPhoneNumber()),
                        jsonPath("$.role").value(userCreatedWithId.getRole().getAuthority()),
                        jsonPath("$.avatar").value(userCreatedWithId.getAvatar()),
                        header().string("Location", "http://localhost/users/js/" + userDto.getId())
                );

        verify(userService, times(1)).processCreateUser(refEq(userCreated), refEq(imageFile));
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenSendUserCreationFormIncorrectData_thenReturnErrorsValidationException() throws Exception {
        UserDto userCreated = DtoTest.getExpectedUserFormCreate();
        userCreated.setPassword("");
        this.mockMvc.perform(multipart("/api/users")
                        .file(imageFile)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DtoTest.convertObjectToJsonBytes(userCreated))
                )
                .andDo(print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );

        verifyNoInteractions(userService);
    }

    @Test
    void whenSendUserCreationFormWithExistingUsername_thenReturnErrorUserAlreadyExists() throws Exception {
        UserDto userCreated = DtoTest.getExpectedUserFormCreate();
        when(userService.processCreateUser(refEq(userCreated), refEq(imageFile)))
                .thenThrow(new UserAlreadyExistsException(""));
        this.mockMvc.perform(multipart("/api/users")
                        .file(imageFile)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DtoTest.convertObjectToJsonBytes(userCreated))
                )
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );

        verify(userService, times(1)).processCreateUser(refEq(userCreated), refEq(imageFile));
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenSendUserUpdateFormCorrectData_thenReturnUpdatedUserEntry() throws Exception {
        when(userService.processUserUpdates(refEq(userDto), refEq(null))).thenReturn(userDto);
        this.mockMvc.perform(put("/api/users/{id}", userDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DtoTest.convertObjectToJsonBytes(userDto))
                )
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(userDto.getId()),
                        jsonPath("$.username").value(userDto.getUsername()),
                        jsonPath("$.password").value(userDto.getPassword()),
                        jsonPath("$.firstName").value(userDto.getFirstName()),
                        jsonPath("$.lastName").value(userDto.getLastName()),
                        jsonPath("$.email").value(userDto.getEmail()),
                        jsonPath("$.phoneNumber").value(userDto.getPhoneNumber()),
                        jsonPath("$.role").value(userDto.getRole().getAuthority()),
                        jsonPath("$.avatar").value(userDto.getAvatar()),
                        header().string("Location", "http://localhost/users/js/" + userDto.getId())
                );

        verify(userService, times(1)).processUserUpdates(refEq(userDto), refEq(null));
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenSendUserUpdateFormIncorrectData_thenReturnErrorsValidationException() throws Exception {
        userDto.setPassword("");
        this.mockMvc.perform(put("/api/users/{id}", userDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DtoTest.convertObjectToJsonBytes(userDto))
                )
                .andDo(print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );

        verifyNoInteractions(userService);
    }

    @Test
    void whenSendUserUpdateFormCorrectDataAndRuntimeExceptionOccurs_thenReturnErrors() throws Exception {
        when(userService.processUserUpdates(refEq(userDto), refEq(null))).thenThrow(RuntimeException.class);
        this.mockMvc.perform(put("/api/users/{id}", userDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DtoTest.convertObjectToJsonBytes(userDto))
                )
                .andDo(print())
                .andExpectAll(
                        status().isInternalServerError(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );

        verify(userService, times(1)).processUserUpdates(refEq(userDto), refEq(null));
        verifyNoMoreInteractions(userService);
    }


    @Test
    void whenRequestUserDeleteOrBlockSuccessfully_thenReturnCorrectStatus() throws Exception {
        when(userService.findById(userDto.getId())).thenReturn(userDto);
        doNothing().when(userService).delete(userDto);
        this.mockMvc.perform(delete("/api/users/{id}", userDto.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(userService, times(1)).findById(userDto.getId());
        verify(userService, times(1)).delete(userDto);
        verifyNoMoreInteractions(userService);
        verify(reservationService, times(1)).findAllByUsers(userDto.getId());
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenRequestUserDeleteOrBlockUnsuccessfully_thenReturnError() throws Exception {
        when(userService.findById(userDto.getId())).thenReturn(userDto);
        doThrow(new UserDeleteException("")).doNothing().when(userService).delete(userDto);
        this.mockMvc.perform(delete("/api/users/{id}", userDto.getId()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(userService, times(1)).findById(userDto.getId());
        verify(userService, times(1)).delete(userDto);
        verifyNoMoreInteractions(userService);
        verify(reservationService, times(1)).findAllByUsers(userDto.getId());
        verifyNoMoreInteractions(reservationService);
    }
}