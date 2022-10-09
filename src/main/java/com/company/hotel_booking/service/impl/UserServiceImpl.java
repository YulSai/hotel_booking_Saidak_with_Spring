package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.controller.command.util.Paging;
import com.company.hotel_booking.data.dao.api.IUserDao;
import com.company.hotel_booking.data.entity.User;
import com.company.hotel_booking.data.mapper.ObjectMapper;
import com.company.hotel_booking.exceptions.LoginUserException;
import com.company.hotel_booking.exceptions.ServiceException;
import com.company.hotel_booking.managers.MessageManager;
import com.company.hotel_booking.service.api.IUserService;
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
public class UserServiceImpl implements IUserService {
    private final IUserDao userDao;
    private final DigestUtil digestUtil;
    private final UserValidator userValidator;
    private final ObjectMapper mapper;

    @Override
    public UserDto findById(Long id) {
        log.debug("Calling a service method findById. UserDto id = {}", id);
        User user = userDao.findById(id);
        if (user == null) {
            log.error("SQLUserService findById error. No user with id = {}", id);
            throw new ServiceException(MessageManager.getMessage("msg.error.find") + id);
        }
        return mapper.toDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        log.debug("Calling a service method findAll");
        return userDao.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public UserDto create(UserDto userDto) {
        log.debug("Calling a service method create. UserDto = {}", userDto);
        if (userDao.findUserByEmail(userDto.getEmail()) != null) {
            log.error("User with email = {} already exists", userDto.getEmail());
            throw new ServiceException(MessageManager.getMessage("msg.error.exists"));
        }
        userValidator.isValid(userDto);
        String hashPassword = digestUtil.hash(userDto.getPassword());
        userDto.setPassword(hashPassword);
        return mapper.toDto(userDao.save(mapper.toEntity(userDto)));
    }

    @Override
    public UserDto update(UserDto userDto) {
        log.debug("Calling a service method update. UserDto = {}", userDto);
        User existing = userDao.findUserByEmail(userDto.getEmail());
        if (existing != null && !existing.getId().equals(userDto.getId())) {
            log.error("User with email = {} already exists", userDto.getEmail());
            throw new ServiceException(MessageManager.getMessage("msg.error.exists"));
        }
        userValidator.isValid(userDto);
        return mapper.toDto(userDao.update(mapper.toEntity(userDto)));
    }

    @Override
    public UserDto changePassword(UserDto userDto) {
        log.debug("Calling a service method changePassword. UserDto = {}", userDto);
        userValidator.isValid(userDto);
        String existPassword = userDao.findById(userDto.getId()).getPassword();
        String hashPassword = digestUtil.hash(userDto.getPassword());
        if (hashPassword.equals(existPassword)) {
            throw new ServiceException(MessageManager.getMessage("msg.error.new.password"));
        }
        userDto.setPassword(hashPassword);
        return mapper.toDto(userDao.update(mapper.toEntity(userDto)));
    }

    @Override
    public void delete(Long id) {
        log.debug("Calling a service method delete. UserDto id = {}", id);
        userDao.delete(id);
        if (!userDao.delete(id)) {
            log.error("SQLUserService deleted error. Failed to delete user with id = {}", id);
            throw new ServiceException(MessageManager.getMessage("msg.error.delete") + id);
        }
    }

    @Override
    public List<UserDto> findAllPages(Paging paging) {
        log.debug("Calling a service method findAll");
        return userDao.findAllPages(paging.getLimit(), paging.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public UserDto login(String email, String password) {
        log.debug("Calling a service method login. UserDto email = {}", email);
        User user = userDao.findUserByEmail(email);
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
        return userDao.countRow();
    }
}