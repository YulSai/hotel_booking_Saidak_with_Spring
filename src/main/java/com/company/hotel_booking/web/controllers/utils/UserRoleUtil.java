package com.company.hotel_booking.web.controllers.utils;

import com.company.hotel_booking.service.dto.UserDto;
import com.company.hotel_booking.utils.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class UserRoleUtil {

    public void checkUserRoleClient(HttpSession session){
        UserDto userDto = (UserDto) session.getAttribute("user");
        if ("CLIENT".equals(userDto.getRole().toString())) {
            throw new ForbiddenException();
        }
    }

    public void checkUserRoleAdmin(HttpSession session){
        UserDto userDto = (UserDto) session.getAttribute("user");
        if ("ADMIN".equals(userDto.getRole().toString())) {
            throw new ForbiddenException();
        }
    }
}
