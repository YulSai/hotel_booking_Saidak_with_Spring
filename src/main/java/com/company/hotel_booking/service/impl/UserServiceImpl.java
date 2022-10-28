package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.service.mapper.UserMapper;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.utils.aspects.logging.annotations.LoginEx;
import com.company.hotel_booking.utils.aspects.logging.annotations.ServiceEx;
import com.company.hotel_booking.data.repository.ReservationRepository;
import com.company.hotel_booking.data.repository.UserRepository;
import com.company.hotel_booking.data.entity.User;
import com.company.hotel_booking.utils.exceptions.LoginUserException;
import com.company.hotel_booking.utils.exceptions.ServiceException;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.service.utils.DigestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Class object UserDTO with implementation of CRUD operation operations
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final DigestUtil digestUtil;

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto findById(Long id) {
        return mapper.toDto(userRepository.findById(id).orElseThrow(() ->
                new ServiceException("msg.user.error.find.by.id")));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto create(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ServiceException("msg.user.error.create.exists");
        }
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
            throw new ServiceException("msg.user.error.update.exists");
        }
        return mapper.toDto(userRepository.save(mapper.toEntity(userDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto changePassword(UserDto userDto) {
        String existPassword = userRepository.findById(userDto.getId()).get().getPassword();
        String hashPassword = digestUtil.hash(userDto.getPassword());
        if (hashPassword.equals(existPassword)) {
            throw new ServiceException("msg.user.error.new.password");
        }
        userDto.setPassword(hashPassword);
        return mapper.toDto(userRepository.save(mapper.toEntity(userDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public void delete(UserDto userDto) {
        if (reservationRepository.findByUserId(userDto.getId()).isEmpty()) {
            userRepository.delete(mapper.toEntity(userDto));
            if (userRepository.existsById(userDto.getId())) {
                throw new ServiceException("msg.user.error.delete");
            }
        } else {
            userRepository.block(userDto.getId());
            if (userRepository.existsById(userDto.getId())) {
                throw new ServiceException("msg.user.error.delete");
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

    @Override
    public UserDto processCreateUser(UserDto userDto, MultipartFile avatarFile) {
        userDto.setAvatar(getAvatarPath(avatarFile));
        return create(userDto);
    }

    @Override
    public UserDto processUserUpdates(UserDto userDto, MultipartFile avatarFile) {
        if (!avatarFile.isEmpty()) {
            userDto.setAvatar(getAvatarPath(avatarFile));
        }
        return update(userDto);
    }

    /**
     * Method writes file and gets path to this file
     *
     * @param avatarFile MultipartFile avatar
     * @return name of file as String
     */
    private String getAvatarPath(MultipartFile avatarFile) {
        String avatarName;
        try {
            if (!avatarFile.isEmpty()) {
                avatarName = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();
                String location = "avatars/";
                File pathFile = new File(location);
                if (!pathFile.exists()) {
                    pathFile.mkdir();
                }
                pathFile = new File(location + avatarName);
                avatarFile.transferTo(pathFile);
            } else {
                avatarName = "defaultAvatar.png";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return avatarName;
    }
}