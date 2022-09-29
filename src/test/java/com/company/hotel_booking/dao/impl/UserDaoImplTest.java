package com.company.hotel_booking.dao.impl;

import com.company.hotel_booking.dao.api.IUserDao;
import com.company.hotel_booking.dao.connection.DataSource;
import com.company.hotel_booking.dao.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDaoImplTest {
    private final IUserDao userDao = new UserDaoImpl(DataSource.getINSTANCE());

    @Test
    public void findById() {
        User actual = userDao.findById(1L);
        User expected = getExpectedUser();
        assertEquals(expected, actual);
    }

    @Test
    public void findUserByEmail() {
        User actual = userDao.findUserByEmail("maxim_hammond@kwontol.com");
        User expected = getExpectedUser();
        assertEquals(expected, actual);
    }

    @Test
    public void countRow() {
        Long actual = userDao.countRow();
        Long expected = 22L;
        assertEquals(expected, actual);
    }

    private User getExpectedUser() {
        User expected = new User();
        expected.setId(1L);
        expected.setFirstName("Maxim");
        expected.setLastName("Hammond");
        expected.setEmail("maxim_hammond@kwontol.com");
        expected.setPassword("DFD0CCF4CEF833270A89F57894F7532443868960");
        expected.setPhoneNumber("+48511906624");
        expected.setRole(User.Role.valueOf("ADMIN"));
        expected.setAvatar("avatar01.png");
        return expected;
    }
}