package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.data.entity.Reservation;
import com.company.hotel_booking.data.entity.User;
import com.company.hotel_booking.data.repository.ReservationRepository;
import com.company.hotel_booking.data.repository.UserRepository;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.service.mapper.UserMapper;
import com.company.hotel_booking.utils.DtoTest;
import com.company.hotel_booking.utils.EntityTest;
import com.company.hotel_booking.utils.TestConstants;
import com.company.hotel_booking.utils.exceptions.users.LoginException;
import com.company.hotel_booking.utils.exceptions.users.UserAlreadyExistsException;
import com.company.hotel_booking.utils.exceptions.users.UserDeleteException;
import com.company.hotel_booking.utils.exceptions.users.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private UserMapper mapper;
    @Mock
    private MessageSource messageSource;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setup() {
        userService = new UserServiceImpl(userRepository, reservationRepository, bCryptPasswordEncoder,
                mapper, messageSource);
        user = EntityTest.getExpectedUserWithoutId();
        userDto = DtoTest.getExpectedUserWithoutId();
    }

    private void mockMapperToEntity() {
        when(mapper.toEntity(userDto)).thenReturn(user);
    }

    private void mockMapperToDto() {
        when(mapper.toDto(user)).thenReturn(userDto);
    }

    @Test
    void getUserPositive() {
        when(userRepository.findById(TestConstants.USER_ID)).thenReturn(Optional.of(user));
        mockMapperToDto();

        UserDto actual = userService.findById(TestConstants.USER_ID);

        assertEquals(userDto, actual);

        verify(userRepository, times(1)).findById(TestConstants.USER_ID);
        verify(mapper, times(1)).toDto(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getUserNotFound() {
        when(userRepository.findById(TestConstants.USER_ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(TestConstants.USER_ID));

        verify(mapper, never()).toDto(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getAllUserPositive() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> pageUser = new PageImpl<>(new ArrayList<>());
        Page<UserDto> pageUserDto = new PageImpl<>(new ArrayList<>());

        when(userRepository.findAll(pageable)).thenReturn(pageUser);
        when(userRepository.findAll(pageable).map(mapper::toDto)).thenReturn(pageUserDto);

        Page<UserDto> actual = userService.findAllPages(pageable);
        assertNotNull(actual);

        verify(userRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void createUserPositive() {
        mockMapperToEntity();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);
        mockMapperToDto();

        UserDto actual = userService.create(userDto);

        assertEquals(userDto, actual);

        verify(userRepository, times(1)).save(any(User.class));
        verify(mapper, times(1)).toEntity(any(UserDto.class));
        verify(mapper, times(1)).toDto(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void createUserAlreadyExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(userDto));

        verify(userRepository, never()).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUserPositive() {
        user = EntityTest.getExpectedUserWithId();
        userDto = DtoTest.getExpectedUserWithId();
        mockMapperToEntity();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(user)).thenReturn(user);
        mockMapperToDto();

        user.setFirstName("Updated");
        user.setLastName("Updated");
        userDto.setFirstName("Updated");
        userDto.setLastName("Updated");

        UserDto actual = userService.update(userDto);
        assertEquals(userDto, actual);

        verify(userRepository, times(1)).save(any(User.class));
        verify(mapper, times(1)).toEntity(any(UserDto.class));
        verify(mapper, times(1)).toDto(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUserAlreadyExistsDifferentId() {
        user = EntityTest.getExpectedUserWithId();
        userDto = DtoTest.getExpectedUserWithId();
        User existing = user;
        existing.setId(20L);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(existing));

        assertThrows(UserAlreadyExistsException.class, () -> userService.update(userDto));

        verify(userRepository, never()).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteUserPositive() {
        user = EntityTest.getExpectedUserWithId();
        userDto = DtoTest.getExpectedUserWithId();
        List<Reservation> data = new ArrayList<>();

        when(reservationRepository.findByUserId(userDto.getId())).thenReturn(data);
        mockMapperToEntity();

        userService.delete(userDto);

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).delete(user);
        verify(mapper, times(1)).toEntity(any(UserDto.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void blockUserPositive() {
        user = EntityTest.getExpectedUserWithId();
        userDto = DtoTest.getExpectedUserWithId();
        List<Reservation> data = new ArrayList<>();
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        data.add(reservation);

        when(reservationRepository.findByUserId(userDto.getId())).thenReturn(data);
        userService.delete(userDto);

        verify(userRepository, times(1)).block(user.getId());
        verify(userRepository, times(1)).checkBlock(user.getId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteUserUserDeleteException() {
        user = EntityTest.getExpectedUserWithId();
        userDto = DtoTest.getExpectedUserWithId();

        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        assertThrows(UserDeleteException.class, () -> userService.delete(userDto));

        verify(userRepository, never()).delete(user);
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void blockUserUserDeleteException() {
        user = EntityTest.getExpectedUserWithId();
        userDto = DtoTest.getExpectedUserWithId();
        List<Reservation> data = new ArrayList<>();
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        data.add(reservation);

        when(reservationRepository.findByUserId(userDto.getId())).thenReturn(data);
        doNothing().when(userRepository).block(user.getId());
        when(userRepository.checkBlock(userDto.getId())).thenReturn(Optional.ofNullable(user));

        assertThrows(UserDeleteException.class, () -> userService.delete(userDto));

        verifyNoMoreInteractions(userRepository);
    }


    @Test
    void findByUsernamePositive() {
        when(userRepository.findByUsername(TestConstants.USER_USERNAME)).thenReturn(Optional.of(user));
        mockMapperToDto();

        UserDto actual = userService.findByUsername(TestConstants.USER_USERNAME);

        assertEquals(userDto, actual);

        verify(userRepository, times(1)).findByUsername(TestConstants.USER_USERNAME);
        verify(mapper, times(1)).toDto(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findByUsernameNotFound() {
        when(userRepository.findByUsername(TestConstants.USER_USERNAME)).thenReturn(Optional.empty());

        assertThrows(LoginException.class, () -> userService.findByUsername(TestConstants.USER_USERNAME));

        verify(mapper, never()).toDto(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void changePasswordPositive() {
        user = EntityTest.getExpectedUserWithId();
        userDto = DtoTest.getExpectedUserWithId();
        mockMapperToEntity();

        when(userRepository.findById(userDto.getId())).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(user)).thenReturn(user);
        mockMapperToDto();

        user.setPassword("Updated");
        userDto.setPassword("Updated2");

        UserDto actual = userService.changePassword(userDto);
        assertEquals(userDto, actual);

        verify(userRepository, times(1)).save(any(User.class));
        verify(mapper, times(1)).toEntity(any(UserDto.class));
        verify(mapper, times(1)).toDto(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void changePasswordAlreadyExists() {
        user = EntityTest.getExpectedUserWithId();
        userDto = DtoTest.getExpectedUserWithId();

        when(userRepository.findById(userDto.getId())).thenReturn(Optional.ofNullable(user));

        user.setPassword("Updated");
        userDto.setPassword("Updated");

        assertThrows(UserAlreadyExistsException.class, () -> userService.changePassword(userDto));

        verify(userRepository, never()).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void processUserCreatePositive() {
        user = EntityTest.getExpectedUserWithoutId();
        userDto = DtoTest.getExpectedUserWithoutId();
        MultipartFile avatarFile = mock(MultipartFile.class);

        mockMapperToEntity();
        when(userRepository.save(user)).thenReturn(user);
        mockMapperToDto();

        UserDto created = userService.processCreateUser(this.userDto, avatarFile);

        assertEquals(userDto, created);

        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).findByUsername(userDto.getUsername());
        verify(mapper, times(1)).toEntity(any(UserDto.class));
        verify(mapper, times(1)).toDto(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void processUserUpdatesPositive() {
        user = EntityTest.getExpectedUserWithId();
        userDto = DtoTest.getExpectedUserWithId();
        MultipartFile avatarFile = mock(MultipartFile.class);

        mockMapperToEntity();
        when(userRepository.save(user)).thenReturn(user);
        mockMapperToDto();

        UserDto updated = userService.processUserUpdates(this.userDto, avatarFile);

        assertEquals(userDto, updated);

        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).findByUsername(userDto.getUsername());
        verify(mapper, times(1)).toEntity(any(UserDto.class));
        verify(mapper, times(1)).toDto(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }
}