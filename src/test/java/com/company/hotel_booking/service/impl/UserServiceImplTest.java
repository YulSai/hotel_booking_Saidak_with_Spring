//package com.company.hotel_booking.service.impl;
//
//import com.company.hotel_booking.dao.impl.UserDaoImpl;
//import com.company.hotel_booking.exceptions.LoginUserException;
//import com.company.hotel_booking.service.api.IUserService;
//import com.company.hotel_booking.service.utils.DigestUtil;
//import com.company.hotel_booking.service.validators.UserValidator;
//import com.zaxxer.hikari.HikariDataSource;
//import org.junit.jupiter.api.Test;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class UserServiceImplTest {
//    private final IUserService userService = new UserServiceImpl(
//            new UserDaoImpl(new JdbcTemplate(), new NamedParameterJdbcTemplate(new HikariDataSource())), new DigestUtil(), new UserValidator());
//
//    @Test
//    public void loginCorrect() {
//        userService.login("maxim_hammond@kwontol.com", "111111");
//        assertTrue(true);
//    }
//
//    @Test
//    public void loginCorrectException() {
//        try {
//            userService.login("maxim_hammond@kwontol.com", "111111");
//        } catch (LoginUserException e) {
//            assertFalse(false);
//        }
//    }
//
//    @Test
//    public void loginIncorrect() throws LoginUserException {
//        try {
//            userService.login("maxim_hammond@kwontol.com", "000");
//        } catch (LoginUserException e) {
//            assertTrue(true);
//        }
//    }
//
//    @Test
//    public void loginNull() throws LoginUserException {
//        try {
//            userService.login(null, null);
//        } catch (LoginUserException e) {
//            assertTrue(true);
//        }
//    }
//}