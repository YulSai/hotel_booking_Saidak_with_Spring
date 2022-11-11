package com.company.hotel_booking.service.impl;

import com.company.hotel_booking.data.entity.User;
import com.company.hotel_booking.data.repository.ReservationRepository;
import com.company.hotel_booking.data.repository.UserRepository;
import com.company.hotel_booking.service.api.UserService;
import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.service.mapper.UserMapper;
import com.company.hotel_booking.service.utils.DigestUtil;
import com.company.hotel_booking.utils.aspects.logging.annotations.ImageUploadingEx;
import com.company.hotel_booking.utils.aspects.logging.annotations.LogInvocationServer;
import com.company.hotel_booking.utils.aspects.logging.annotations.LoginEx;
import com.company.hotel_booking.utils.aspects.logging.annotations.NotFoundEx;
import com.company.hotel_booking.utils.aspects.logging.annotations.ServiceEx;
import com.company.hotel_booking.utils.constants.PagesConstants;
import com.company.hotel_booking.utils.exceptions.users.ImageUploadingException;
import com.company.hotel_booking.utils.exceptions.users.LoginException;
import com.company.hotel_booking.utils.exceptions.users.UserAlreadyExistsException;
import com.company.hotel_booking.utils.exceptions.users.UserDeleteException;
import com.company.hotel_booking.utils.exceptions.users.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final MessageSource messageSource;

    @Override
    @LogInvocationServer
    @NotFoundEx
    public UserDto findById(Long id) {
        return mapper.toDto(userRepository.findById(id)
                .orElseThrow(() ->
                new UserNotFoundException(messageSource.getMessage("msg.user.error.find.by.id", null,
                        LocaleContextHolder.getLocale()))));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto create(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(messageSource.getMessage("msg.user.error.create.exists", null,
                    LocaleContextHolder.getLocale()));
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
            throw new UserAlreadyExistsException(
                    messageSource.getMessage("msg.user.error.update.exists", null,
                            LocaleContextHolder.getLocale()));
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
            throw new UserAlreadyExistsException(messageSource.getMessage("msg.user.error.new.password", null,
                    LocaleContextHolder.getLocale()));
        }
        userDto.setPassword(hashPassword);
        return mapper.toDto(userRepository.save(mapper.toEntity(userDto)));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    @Transactional
    public void delete(UserDto userDto) {
        if (reservationRepository.findByUserId(userDto.getId()).isEmpty()) {
            userRepository.delete(mapper.toEntity(userDto));
            if (userRepository.findById(userDto.getId()).isPresent()) {
                throw new UserDeleteException(messageSource.getMessage("msg.user.error.delete", null,
                        LocaleContextHolder.getLocale()));
            }
        } else {
            userRepository.block(userDto.getId());
            if (userRepository.checkBlock(userDto.getId()).isPresent()) {
                throw new UserDeleteException(messageSource.getMessage("msg.user.error.delete", null,
                        LocaleContextHolder.getLocale()));
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
                .orElseThrow(LoginException::new));
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto processCreateUser(UserDto userDto, MultipartFile avatarFile) {
        userDto.setAvatar(getAvatarPath(avatarFile));
        return create(userDto);
    }

    @Override
    @LogInvocationServer
    @ServiceEx
    public UserDto processUserUpdates(UserDto userDto, MultipartFile avatarFile) {
        if (avatarFile != null) {
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
    @LogInvocationServer
    @ImageUploadingEx
    private String getAvatarPath(MultipartFile avatarFile) {
        String avatarName;
        try {
            if (avatarFile.getSize() > 0) {
                avatarName = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();
                String location = "avatars/";
                File pathFile = new File(PagesConstants.LOCATION_IMAGES);
                if (!pathFile.exists()) {
                    pathFile.mkdir();
                }
                pathFile = new File(location + avatarName);
                avatarFile.transferTo(pathFile);
            } else {
                avatarName = "defaultAvatar.png";
            }
        } catch (IOException e) {
            throw new ImageUploadingException(messageSource.getMessage("msg.user.error.uploading.image", null,
                    LocaleContextHolder.getLocale()), e);
        }
        return avatarName;
    }
}