package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.data.repository.api.ReservationRepository;
import com.company.hotel_booking.data.repository.api.UserRepository;
import com.company.hotel_booking.data.entity.User;
import com.company.hotel_booking.data.mapper.ObjectMapper;
import com.company.hotel_booking.exceptions.LoginUserException;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.service.utils.DigestUtil;
import com.company.hotel_booking.service.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class object UserDTO with implementation of CRUD operation operations
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final DigestUtil digestUtil;
    private final UserValidator userValidator;
    private final ObjectMapper mapper;

    @Override
    public UserDto findById(Long id) {
        log.debug("Calling a service method findById. UserDto id = {}", id);
        User user = userRepository.findById(id);
        if (user == null) {
            log.error("SQLUserService findById error. No user with id = {}", id);
            throw new ServiceException(MessageManager.getMessage("msg.error.find") + id);
        }
        return mapper.toDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        log.debug("Calling a service method findAll");
        return userRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public UserDto create(UserDto userDto) {
        log.debug("Calling a service method create. UserDto = {}", userDto);
        if (userRepository.findUserByEmail(userDto.getEmail()) != null) {
            log.error("User with email = {} already exists", userDto.getEmail());
            throw new ServiceException(MessageManager.getMessage("msg.error.exists"));
        }
        userValidator.isValid(userDto);
        String hashPassword = digestUtil.hash(userDto.getPassword());
        userDto.setPassword(hashPassword);
        return mapper.toDto(userRepository.create(mapper.toEntity(userDto)));
    }

    @Override
    public UserDto update(UserDto userDto) {
        log.debug("Calling a service method update. UserDto = {}", userDto);
        User existing = (User) userRepository.findUserByEmail(userDto.getEmail());
        if (existing != null && !existing.getId().equals(userDto.getId())) {
            log.error("User with email = {} already exists", userDto.getEmail());
            throw new ServiceException(MessageManager.getMessage("msg.error.exists"));
        }
        userValidator.isValid(userDto);
        return mapper.toDto(userRepository.update(mapper.toEntity(userDto)));
    }

    @Override
    public UserDto changePassword(UserDto userDto) {
        log.debug("Calling a service method changePassword. UserDto = {}", userDto);
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
    public void delete(Long id) {
        log.debug("Calling a service method delete. UserDto id = {}", id);
        if (reservationRepository.findAllByUsers(id).isEmpty()) {
            int resultDeleted = userRepository.delete(id);
            if (resultDeleted != 1) {
                log.error("SQLUserService deleted error. Failed to delete user with id = {}", id);
                throw new ServiceException(MessageManager.getMessage("msg.error.delete") + id);
            }
        } else {
            int resultBlock = userRepository.block(id);
            if (resultBlock != 1) {
                log.error("SQLUserService deleted error. Failed to delete user with id = {}", id);
                throw new ServiceException(MessageManager.getMessage("msg.error.block") + id);
            }
        }
    }

    @Override
    public List<UserDto> findAllPages(Paging paging) {
        log.debug("Calling a service method findAll");
        return userRepository.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public UserDto login(String email, String password) {
        log.debug("Calling a service method login. UserDto email = {}", email);
        User user = (User) userRepository.findUserByEmail(email);
        if (user == null) {
            log.error("SQLUserService login error. No user with email = {}", email);
            throw new LoginUserException();
        } else {
            UserDto userDto = mapper.toDto(user);
            String hashPassword = digestUtil.hash(password);
            if (!userDto.getPassword().equals(hashPassword)) {
                log.error("SQLUserService login error. Wrong password");
                throw new LoginUserException();
            }
            return userDto;
        }
    }

    @Override
    public long countRow() {
        log.debug("Calling a service method countRow");
        return userRepository.countRow();
    }
}