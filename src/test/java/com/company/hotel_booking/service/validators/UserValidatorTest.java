package com.company.hotel_booking.service.validators;

import com.company.hotel_booking.exceptions.RegistrationException;
import com.company.hotel_booking.managers.MessageManger;
import com.company.hotel_booking.service.dto.UserDto;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidatorTest {

    private final MessageManger messageManger = new MessageManger(Locale.UK);
    private final UserValidator userValidator = new UserValidator();

    @Test
    public void isValidCorrect() {
        try {
            userValidator.isValid(getUserCorrect());
        } catch (RegistrationException e) {
            assertFalse(false);
        }
    }

    @Test
    public void isValidIncorrectFirstNameFormat() {
        UserDto user = getUserCorrect();
        user.setFirstName("Maxim123");
        try {
            userValidator.isValid(user);
        } catch (RegistrationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void isValidIncorrectFirstNameEmpty() {
        UserDto user = getUserCorrect();
        user.setFirstName("");
        try {
            userValidator.isValid(user);
        } catch (RegistrationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void isValidIncorrectLastNameEmpty() {
        UserDto user = getUserCorrect();
        user.setLastName("");
        try {
            userValidator.isValid(user);
        } catch (RegistrationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void isValidIncorrectLastNameFormat() {
        UserDto user = getUserCorrect();
        user.setFirstName("4Agh");
        try {
            userValidator.isValid(user);
        } catch (RegistrationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void isValidIncorrectEmailEmpty() {
        UserDto user = getUserCorrect();
        user.setFirstName("");
        try {
            userValidator.isValid(user);
        } catch (RegistrationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void isValidIncorrectEmailFormat() {
        UserDto user = getUserCorrect();
        user.setFirstName("maxim.com");
        try {
            userValidator.isValid(user);
        } catch (RegistrationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void isValidIncorrectPasswordEmpty() {
        UserDto user = getUserCorrect();
        user.setFirstName("");
        try {
            userValidator.isValid(user);
        } catch (RegistrationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void isValidIncorrectPasswordFormat() {
        UserDto user = getUserCorrect();
        user.setFirstName("Какой-то пароль");
        try {
            userValidator.isValid(user);
        } catch (RegistrationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void isValidIncorrectPasswordShort() {
        UserDto user = getUserCorrect();
        user.setFirstName("123");
        try {
            userValidator.isValid(user);
        } catch (RegistrationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void isValidIncorrectPhoneNumberEmpty() {
        UserDto user = getUserCorrect();
        user.setFirstName("");
        try {
            userValidator.isValid(user);
        } catch (RegistrationException e) {
            assertTrue(true);
        }
    }

    private UserDto getUserCorrect() {
        UserDto expected = new UserDto();
        expected.setId(1L);
        expected.setFirstName("Maxim");
        expected.setLastName("Hammond");
        expected.setEmail("maxim_hammond@kwontol.com");
        expected.setPassword("111111");
        expected.setPhoneNumber("+48511906624");
        expected.setRole(UserDto.RoleDto.valueOf("ADMIN"));
        expected.setAvatar("avatar01.png");
        return expected;
    }
}