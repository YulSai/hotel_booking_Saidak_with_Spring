package com.company.hotel_booking.web.controllers.view;

import com.company.hotel_booking.service.api.ReservationService;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.utils.DtoTest;
import com.company.hotel_booking.utils.constants.PagesConstants;
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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private PagingUtil pagingUtil;
    @MockBean
    private MessageSource messageSource;

    private UserDto userDto;
    private MockMultipartFile imageFile;

    @BeforeEach
    public void setup() {
        userDto = DtoTest.getExpectedUserWithId();
        imageFile = new MockMultipartFile("avatarFile", "avatar_test.png",
                "text/plain", "some xml".getBytes());
    }

    @Test
    void whenRequestAllUsers_thenReturnCorrectView() throws Exception {
        List<UserDto> users = Collections.singletonList(userDto);
        Page<UserDto> pageUserDto = new PageImpl<>(new ArrayList<>(users));
        when(userService.findAllPages(any())).thenReturn(pageUserDto);
        this.mockMvc.perform(get("/users/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("users", users),
                        view().name(PagesConstants.PAGE_USERS),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/users/users.jsp"));

        verify(userService, times(1)).findAllPages(any());
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenRequestAllUsersEmpty_thenReturnCorrectView() throws Exception {
        Page<UserDto> pageUserDto = new PageImpl<>(new ArrayList<>());
        when(userService.findAllPages(any())).thenReturn(pageUserDto);
        this.mockMvc.perform(get("/users/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("message", messageSource.getMessage("msg.users.no.users", null,
                                LocaleContextHolder.getLocale())),
                        view().name(PagesConstants.PAGE_USERS),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/users/users.jsp"));

        verify(userService, times(1)).findAllPages(any());
        verifyNoMoreInteractions(userService);
    }

    @Test
    @WithMockUser
    void whenRequestOneUserById_thenReturnCorrectViewWithModelAttribute() throws Exception {
        when(userService.findByUsername("user")).thenReturn(userDto);
        when(userService.findById(userDto.getId())).thenReturn(userDto);
        this.mockMvc.perform(
                        get("/users/{id}", userDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("user", userDto),
                        view().name(PagesConstants.PAGE_USER),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/users/userInfo.jsp"));

        verify(userService, times(1)).findByUsername("user");
        verify(userService, times(1)).findById(userDto.getId());
        verifyNoMoreInteractions(userService);
    }

    @Test
    @WithMockUser
    void whenRequestNonExistingUser_thenReturnCorrectViewAndExceptionMessage() throws Exception {
        when(userService.findByUsername("user")).thenReturn(userDto);
        when(userService.findById(userDto.getId())).thenThrow(new UserNotFoundException(""));
        this.mockMvc.perform(get("/users/{id}", userDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        model().attributeExists("message"),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp"));
        verify(userService, times(1)).findByUsername("user");
        verify(userService, times(1)).findById(userDto.getId());
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenRequestUserCreationForm_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/users/create"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name(PagesConstants.PAGE_CREATE_USER),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/users/createUserForm.jsp"));

        verifyNoInteractions(userService);
    }

    @Test
    void whenSendUserCreationFormCorrectData_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        UserDto userCreated = DtoTest.getExpectedUserFormCreate();
        UserDto userCreatedWithId = DtoTest.getExpectedUserFormCreateWithId();

        when(userService.processCreateUser(refEq(userCreated), refEq(imageFile))).thenReturn(userCreatedWithId);
        this.mockMvc.perform(multipart("/users/create")
                        .file(imageFile)
                        .param("username", userCreated.getUsername())
                        .param("password", userCreated.getPassword())
                        .param("firstName", userCreated.getFirstName())
                        .param("lastName", userCreated.getLastName())
                        .param("email", userCreated.getEmail())
                        .param("phoneNumber", userCreated.getPhoneNumber())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/users/" + userCreatedWithId.getId()),
                        request().sessionAttribute("message",
                                messageSource.getMessage("msg.user.created", null,
                                        LocaleContextHolder.getLocale())));

        verify(userService, times(1)).processCreateUser(refEq(userCreated), refEq(imageFile));
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenSendUserCreationFormIncorrectData_thenGetErrorsAndReturnSamePage() throws Exception {
        UserDto userCreated = DtoTest.getExpectedUserFormCreate();
        this.mockMvc.perform(post("/users/create")
                        .param("firstName", userCreated.getFirstName())
                        .param("lastName", userCreated.getLastName())
                        .param("email", userCreated.getEmail())
                        .param("phoneNumber", userCreated.getPhoneNumber())
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name(PagesConstants.PAGE_CREATE_USER),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/users/createUserForm.jsp"));

        verifyNoInteractions(userService);
    }

    @Test
    void whenSendUserCreationFormWithExistingUsername_thenReturnErrorViewAndExceptionMessage() throws Exception {
        UserDto userCreated = DtoTest.getExpectedUserFormCreate();
        when(userService.processCreateUser(refEq(userCreated), refEq(imageFile)))
                .thenThrow(new UserAlreadyExistsException(""));
        this.mockMvc.perform(multipart("/users/create")
                        .file(imageFile)
                        .param("username", userCreated.getUsername())
                        .param("password", userCreated.getPassword())
                        .param("firstName", userCreated.getFirstName())
                        .param("lastName", userCreated.getLastName())
                        .param("email", userCreated.getEmail())
                        .param("phoneNumber", userCreated.getPhoneNumber())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        model().attributeExists("message"),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp"));

        verify(userService, times(1)).processCreateUser(refEq(userCreated), refEq(imageFile));
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenRequestUserUpdateForm_thenReturnCorrectView() throws Exception {
        when(userService.findById(userDto.getId())).thenReturn(userDto);
        this.mockMvc.perform(get("/users/update/{id}", userDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("user", userDto),
                        view().name(PagesConstants.PAGE_UPDATE_USER),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/users/updateUserForm.jsp"));

        verify(userService, times(1)).findById(userDto.getId());
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenSendUserUpdateFormCorrectData_thenReceiveCorrectDataAndDoRedirectWithMessage() throws Exception {
        when(userService.processUserUpdates(refEq(userDto), refEq(imageFile))).thenReturn(userDto);
        this.mockMvc.perform(multipart("/users/update/{id}", userDto.getId())
                        .file(imageFile)
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("firstName", userDto.getFirstName())
                        .param("lastName", userDto.getLastName())
                        .param("email", userDto.getEmail())
                        .param("phoneNumber", userDto.getPhoneNumber())
                        .param("role", userDto.getRole().toString())
                        .param("avatar", userDto.getAvatar())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/users/" + userDto.getId()),
                        request().sessionAttribute("message", messageSource.getMessage("msg.user.updated",
                                null, LocaleContextHolder.getLocale()))
                );

        verify(userService, times(1)).processUserUpdates(refEq(userDto), refEq(imageFile));
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenSendUserUpdateFormIncorrectData_thenGetErrorsAndReturnSamePage() throws Exception {
        this.mockMvc.perform(multipart("/users/update/{id}", userDto.getId())
                        .file(imageFile)
                        .param("firstName", userDto.getFirstName())
                        .param("lastName", userDto.getLastName())
                        .param("email", userDto.getEmail())
                        .param("phoneNumber", userDto.getPhoneNumber())
                        .param("role", userDto.getRole().toString())
                        .param("avatar", userDto.getAvatar())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("user", userDto),
                        view().name(PagesConstants.PAGE_UPDATE_USER),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/users/updateUserForm.jsp"));

        verifyNoInteractions(userService);
    }

    @Test
    void whenSendUserUpdateFormCorrectDataAndRuntimeExceptionOccurs_thenReturnErrorPage() throws Exception {
        when(userService.processUserUpdates(refEq(userDto), refEq(imageFile))).thenThrow(RuntimeException.class);
        this.mockMvc.perform(multipart("/users/update/{id}", userDto.getId())
                        .file(imageFile)
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("firstName", userDto.getFirstName())
                        .param("lastName", userDto.getLastName())
                        .param("email", userDto.getEmail())
                        .param("phoneNumber", userDto.getPhoneNumber())
                        .param("role", userDto.getRole().toString())
                        .param("avatar", userDto.getAvatar())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andDo(print())
                .andExpectAll(
                        status().isInternalServerError(),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp"));

        verify(userService, times(1)).processUserUpdates(refEq(userDto), refEq(imageFile));
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenRequestUserDeleteOrBlockSuccessfully_thenReturnCorrectMessageAndView() throws Exception {
        when(userService.findById(userDto.getId())).thenReturn(userDto);
        doNothing().when(userService).delete(userDto);
        this.mockMvc.perform(get("/users/delete/{id}", userDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/users/all"),
                        view().name("redirect:/users/all"),
                        redirectedUrl("/users/all"),
                        request().sessionAttribute("message", messageSource.getMessage("msg.user.deleted",
                                null, LocaleContextHolder.getLocale())));

        verify(userService, times(1)).findById(userDto.getId());
        verify(userService, times(1)).delete(userDto);
        verifyNoMoreInteractions(userService);
        verify(reservationService, times(1)).findAllByUsers(userDto.getId());
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenRequestUserDeleteOrBlockUnsuccessfully_thenReturnErrorViewAndExceptionMessage() throws Exception {
        when(userService.findById(userDto.getId())).thenReturn(userDto);
        doThrow(new UserDeleteException("")).doNothing().when(userService).delete(userDto);
        this.mockMvc.perform(get("/users/delete/{id}", userDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        model().attributeExists("message"),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp"));
        verify(userService, times(1)).findById(userDto.getId());
        verify(userService, times(1)).delete(userDto);
        verifyNoMoreInteractions(userService);
        verify(reservationService, times(1)).findAllByUsers(userDto.getId());
        verifyNoMoreInteractions(reservationService);
    }

    @Test
    void whenRequestUserChangePasswordForm_thenReturnCorrectView() throws Exception {
        when(userService.findById(userDto.getId())).thenReturn(userDto);
        this.mockMvc.perform(get("/users/change_password/{id}", userDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("user", userDto),
                        view().name(PagesConstants.PAGE_CHANGE_PASSWORD),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/users/changePasswordForm.jsp"));

        verify(userService, times(1)).findById(userDto.getId());
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenSendUserChangePasswordFormCorrectData_thenReceiveCorrectDataAndDoRedirectWithMessage() throws Exception {
        when(userService.changePassword(userDto)).thenReturn(userDto);
        this.mockMvc.perform(post("/users/change_password/{id}", userDto.getId())
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("firstName", userDto.getFirstName())
                        .param("lastName", userDto.getLastName())
                        .param("email", userDto.getEmail())
                        .param("phoneNumber", userDto.getPhoneNumber())
                        .param("role", userDto.getRole().toString())
                        .param("avatar", userDto.getAvatar()))
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/users/" + userDto.getId()),
                        request().sessionAttribute("message", messageSource.getMessage
                                ("msg.user.password.change", null, LocaleContextHolder.getLocale())));

        verify(userService, times(1)).changePassword(userDto);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenSendUserChangePasswordFormIncorrectData_thenGetErrorsAndReturnSamePage() throws Exception {
        this.mockMvc.perform(post("/users/change_password/{id}", userDto.getId())
                        .param("username", userDto.getUsername())
                        .param("firstName", userDto.getFirstName())
                        .param("lastName", userDto.getLastName())
                        .param("email", userDto.getEmail())
                        .param("phoneNumber", userDto.getPhoneNumber())
                        .param("role", userDto.getRole().toString())
                        .param("avatar", userDto.getAvatar()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("user", userDto),
                        view().name(PagesConstants.PAGE_CHANGE_PASSWORD),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/users/changePasswordForm.jsp"));

        verifyNoInteractions(userService);
    }

    @Test
    void whenSendUserChangePasswordFormWithExistingUsername_thenReturnErrorViewAndExceptionMessage() throws Exception {
        when(userService.changePassword(userDto)).thenThrow(new UserAlreadyExistsException(""));
        this.mockMvc.perform(post("/users/change_password/{id}", userDto.getId())
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("firstName", userDto.getFirstName())
                        .param("lastName", userDto.getLastName())
                        .param("email", userDto.getEmail())
                        .param("phoneNumber", userDto.getPhoneNumber())
                        .param("role", userDto.getRole().toString())
                        .param("avatar", userDto.getAvatar()))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        model().attributeExists("message"),
                        view().name(PagesConstants.PAGE_ERROR),
                        forwardedUrl("/WEB-INF/jsp/error.jsp"));

        verify(userService, times(1)).changePassword(userDto);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenRequestUserUpdateRoleForm_thenReturnCorrectView() throws Exception {
        when(userService.findById(userDto.getId())).thenReturn(userDto);
        this.mockMvc.perform(get("/users/update_role/{id}", userDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        model().attribute("user", userDto),
                        view().name(PagesConstants.PAGE_UPDATE_USERS_ROLE),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/users/updateUserRoleForm.jsp"));

        verify(userService, times(1)).findById(userDto.getId());
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenSendUserUpdateRoleFormCorrectData_thenReceiveCorrectDataAndDoRedirectWithMessage() throws Exception {
        when(userService.update(userDto)).thenReturn(userDto);
        this.mockMvc.perform(post("/users/update_role/{id}", userDto.getId())
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("firstName", userDto.getFirstName())
                        .param("lastName", userDto.getLastName())
                        .param("email", userDto.getEmail())
                        .param("phoneNumber", userDto.getPhoneNumber())
                        .param("role", userDto.getRole().toString())
                        .param("avatar", userDto.getAvatar()))
                .andDo(print())
                .andExpectAll(
                        status().is3xxRedirection(),
                        header().string("Location", "/users/" + userDto.getId()),
                        request().sessionAttribute("message", messageSource.getMessage("msg.user.updated",
                                null, LocaleContextHolder.getLocale())));

        verify(userService, times(1)).update(userDto);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void whenRequestAllUsersJS_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/users/js/all"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name(PagesConstants.PAGE_USERS_JS),
                        forwardedUrl("/WEB-INF/jsp/forms/rest_js/users/users_js.jsp"));

        verifyNoInteractions(userService);
    }

    @Test
    void whenRequestOneUserByIdJS_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/users/js/{id}", userDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name(PagesConstants.PAGE_USER_JS),
                        forwardedUrl("/WEB-INF/jsp/forms/rest_js/users/user_js.jsp"));

        verifyNoInteractions(userService);
    }


    @Test
    void whenRequestDeleteOrBlockUserJS_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/users/js/delete/{id}", userDto.getId()))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name(PagesConstants.PAGE_DELETE_USER),
                        forwardedUrl("/WEB-INF/jsp/forms/jstl/users/deleteUser.jsp"));

        verifyNoInteractions(userService);
    }
}