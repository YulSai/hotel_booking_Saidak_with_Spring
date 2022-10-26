package com.company.hotel_booking.service.validators;

import com.company.hotel_booking.utils.aspects.logging.annotations.RegistrationEx;
import com.company.hotel_booking.utils.exceptions.RegistrationException;
import com.company.hotel_booking.utils.managers.MessageManager;
import com.company.hotel_booking.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Class with method for validating user's email and password
 */
@Component
@RequiredArgsConstructor
public class UserValidator {

    private final MessageManager messageManager;

    /**
     * Method checks if the email and password is valid
     *
     * @param userDto User email
     */
    @RegistrationEx
    public void isValid(UserDto userDto) {

        String firstName = userDto.getFirstName();
        if (firstName == null || ("").equals(firstName)) {
            throw new RegistrationException(messageManager.getMessage("msg.error.first.name.empty"));
        }
        if (!firstName.matches("^[A-Za-z-А-Яа-я]+")) {
            throw new RegistrationException(messageManager.getMessage("msg.error.first.name.format"));
        }

        String lastName = userDto.getLastName();
        if (lastName == null || ("").equals(lastName)) {
            throw new RegistrationException(messageManager.getMessage("msg.error.last.name.empty"));
        }
        if (!lastName.matches("^[A-Za-z-А-Яа-я]+")) {
            throw new RegistrationException(messageManager.getMessage("msg.error.last.name.format"));
        }

        String email = userDto.getEmail();
        if (email == null || ("").equals(email)) {
            throw new RegistrationException(messageManager.getMessage("msg.error.email.empty"));
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new RegistrationException(messageManager.getMessage("msg.error.email.format"));
        }

        String password = userDto.getPassword();
        if (password == null || ("").equals(password)) {
            throw new RegistrationException(messageManager.getMessage("msg.error.password.empty"));
        }
        if (!password.matches("[A-Za-z0-9_]+")) {
            throw new RegistrationException(messageManager.getMessage("msg.error.password.format"));
        }
        if (password.length() < 6) {
            throw new RegistrationException(messageManager.getMessage("msg.error.password.length"));
        }

        String phoneNumber = userDto.getPhoneNumber();
        if (phoneNumber == null || ("").equals(phoneNumber)) {
            throw new RegistrationException(messageManager.getMessage("msg.error.phone.empty"));
        }
    }
}