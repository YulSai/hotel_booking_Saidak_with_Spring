package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.aspects.logging.LogInvocationServer;
import com.company.hotel_booking.aspects.logging.LoginEx;
import com.company.hotel_booking.aspects.logging.ServiceEx;
import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.data.repository.api.ReservationRepository;
import com.company.hotel_booking.data.repository.api.UserRepository;
import com.company.hotel_booking.data.entity.User;
import com.company.hotel_booking.service.mapper.ObjectMapper;
import com.company.hotel_booking.exceptions.LoginUserException;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.service.utils.DigestUtil;
import com.company.hotel_booking.service.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class object UserDTO with implementation of CRUD operation operations
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final DigestUtil digestUtil;
    private final UserValidator userValidator;
    private final ObjectMapper mapper;

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto findById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new ServiceException(MessageManager.getMessage("msg.error.find") + id);
        }
        return mapper.toDto(user);
    }

    @Override
    @LogInvocationServer
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto create(UserDto userDto) {
        if (userRepository.findUserByEmail(userDto.getEmail()) != null) {
            throw new ServiceException(MessageManager.getMessage("msg.error.exists"));
        }
        userValidator.isValid(userDto);
        String hashPassword = digestUtil.hash(userDto.getPassword());
        userDto.setPassword(hashPassword);
        return mapper.toDto(userRepository.create(mapper.toEntity(userDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto update(UserDto userDto) {
        User existing = (User) userRepository.findUserByEmail(userDto.getEmail());
        if (existing != null && !existing.getId().equals(userDto.getId())) {
            throw new ServiceException(MessageManager.getMessage("msg.error.exists"));
        }
        userValidator.isValid(userDto);
        return mapper.toDto(userRepository.update(mapper.toEntity(userDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto changePassword(UserDto userDto) {
        userValidator.isValid(userDto);
        String existPassword = userRepository.findById(userDto.getId()).getPassword();
        String hashPassword = digestUtil.hash(userDto.getPassword());
        if (hashPassword.equals(existPassword)) {
            throw new ServiceException(MessageManager.getMessage("msg.error.new.password"));
        }
        userDto.setPassword(hashPassword);
        return mapper.toDto(userRepository.update(mapper.toEntity(userDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public void delete(Long id) {
        if (reservationRepository.findAllByUsers(id).isEmpty()) {
            int resultDeleted = userRepository.delete(id);
            if (resultDeleted != 1) {
                throw new ServiceException(MessageManager.getMessage("msg.error.delete") + id);
            }
        } else {
            int resultBlock = userRepository.block(id);
            if (resultBlock != 1) {
                throw new ServiceException(MessageManager.getMessage("msg.error.block") + id);
            }
        }
    }

    @Override
    @LogInvocationServer
    public List<UserDto> findAllPages(Paging paging) {
        return userRepository.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @LogInvocationServer
    @LoginEx
    public UserDto login(String email, String password) {
        User user = (User) userRepository.findUserByEmail(email);
        if (user == null) {
            throw new LoginUserException();
        } else {
            UserDto userDto = mapper.toDto(user);
            String hashPassword = digestUtil.hash(password);
            if (!userDto.getPassword().equals(hashPassword)) {
                throw new LoginUserException();
            }
            return userDto;
        }
    }

    @Override
    @LogInvocationServer
    public long countRow() {
        return userRepository.countRow();
    }
}