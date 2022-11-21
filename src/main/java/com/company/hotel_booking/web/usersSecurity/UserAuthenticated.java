package com.company.hotel_booking.web.usersSecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class UserAuthenticated extends User {

    private final Long id;
    private final String firstName;


    public UserAuthenticated(String username, String password, Collection<? extends GrantedAuthority> authorities,
                             Long id, String firstName) {
        super(username, password, authorities);
        this.id = id;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
}
