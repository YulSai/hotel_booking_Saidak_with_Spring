package com.company.hotel_booking.service.validators;

import com.company.hotel_booking.exceptions.RegistrationException;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.service.dto.UserDto;
import lombok.extern.log4j.Log4j2;

/**
 * Class with method for validating user's email and password
 */
@Log4j2
public class UserValidator {
    private static UserValidator INSTANCE;

    /**
     * Method gets an instance of the class object
     *
     * @return an instance of the class object
     */
    public static UserValidator getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new UserValidator();
        }
        return INSTANCE;
    }

    /**
     * Method checks if the email and password is valid
     *
     * @param userDto User email
     */
    public void isValid(UserDto userDto) {

        String firstName = userDto.getFirstName();
        if (firstName == null || ("").equals(firstName)) {
            log.error("Invalid input first name empty");
            throw new RegistrationException(MessageManger.getMessage("msg.error.first.name.empty"));
        }
        if (!firstName.matches("^[A-Za-z]+")) {
            log.error("Invalid input last name format");
            throw new RegistrationException(MessageManger.getMessage("msg.error.first.name.format"));
        }

        String lastName = userDto.getLastName();
        if (lastName == null || ("").equals(lastName)) {
            log.error("Invalid input last name empty");
            throw new RegistrationException(MessageManger.getMessage("msg.error.last.name.empty"));
        }
        if (!lastName.matches("^[A-Za-z-]+")) {
            log.error("Invalid input last name format");
            throw new RegistrationException(MessageManger.getMessage("msg.error.last.name.format"));
        }

        String email = userDto.getEmail();
        if (email == null || ("").equals(email)) {
            log.error("Invalid input email empty");
            throw new RegistrationException(MessageManger.getMessage("msg.error.email.empty"));
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            log.error("Invalid input email format");
            throw new RegistrationException(MessageManger.getMessage("msg.error.email.format"));
        }

        String password = userDto.getPassword();
        if (password == null || ("").equals(password)) {
            log.error("Invalid input password empty");
            throw new RegistrationException(MessageManger.getMessage("msg.error.password.empty"));
        }
        if (!password.matches("[A-Za-z0-9_]+")) {
            log.error("Invalid input password format");
            throw new RegistrationException(MessageManger.getMessage("msg.error.password.format"));
        }
        if (password.length() < 6) {
            log.error("Invalid input password short");
            throw new RegistrationException(MessageManger.getMessage("msg.error.password.length"));
        }

        String phoneNumber = userDto.getPhoneNumber();
        if (phoneNumber == null || ("").equals(phoneNumber)) {
            log.error("Invalid input phoneNumber empty");
            throw new RegistrationException(MessageManger.getMessage("msg.error.phone.empty"));
        }
    }
}