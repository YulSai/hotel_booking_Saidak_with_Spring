package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.aspects.logging.annotations.LoginEx;
import com.company.hotel_booking.aspects.logging.annotations.ServiceEx;
import com.company.hotel_booking.data.repository.ReservationRepository;
import com.company.hotel_booking.data.repository.UserRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return mapper.toDto(userRepository.findById(id).orElseThrow(() ->
                new ServiceException(MessageManager.getMessage("msg.user.error.find.by.id") + id)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto create(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ServiceException(MessageManager.getMessage("msg.user.error.create.exists"));
        }
        userValidator.isValid(userDto);
        String hashPassword = digestUtil.hash(userDto.getPassword());
        userDto.setPassword(hashPassword);
        return mapper.toDto(userRepository.save(mapper.toEntity(userDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto update(UserDto userDto) {
        User existing = userRepository.findByEmail(userDto.getEmail()).get();
        if (existing != null && !existing.getId().equals(userDto.getId())) {
            throw new ServiceException(MessageManager.getMessage("msg.user.error.update.exists"));
        }
        userValidator.isValid(userDto);
        return mapper.toDto(userRepository.save(mapper.toEntity(userDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto changePassword(UserDto userDto) {
        userValidator.isValid(userDto);
        String existPassword = userRepository.findById(userDto.getId()).get().getPassword();
        String hashPassword = digestUtil.hash(userDto.getPassword());
        if (hashPassword.equals(existPassword)) {
            throw new ServiceException(MessageManager.getMessage("msg.user.error.new.password"));
        }
        userDto.setPassword(hashPassword);
        return mapper.toDto(userRepository.save(mapper.toEntity(userDto)));
    }


    @Override
    @LogInvocationServer
    @ServiceEx
    public void delete(UserDto userDto) {
        userRepository.delete(mapper.toEntity(userDto));
        if (reservationRepository.findByUserId(userDto.getId()).isEmpty()) {
            userRepository.delete(mapper.toEntity(userDto));
            if (userRepository.existsById(userDto.getId())) {
                throw new ServiceException(MessageManager.getMessage("msg.user.error.delete") + userDto.getId());
            }
        } else {
            userRepository.block(userDto.getId());
            if (userRepository.existsById(userDto.getId())) {
                throw new ServiceException(MessageManager.getMessage("msg.user.error.delete") + userDto.getId());
            }
        }
    }

    @Override
    @LogInvocationServer
    public Page<UserDto> findAllPages(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    @LogInvocationServer
    @LoginEx
    public UserDto login(String email, String password) {
        return mapper.toDto(userRepository.findByEmail(email)
                .stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(digestUtil.hash(password)))
                .findFirst()
                .orElseThrow(LoginUserException::new));
    }
}