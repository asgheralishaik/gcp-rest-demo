package com.people.restdemo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Custom implementation to return userdetails for spring security
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username,"$2y$12$QnBKFHfiEi.LjjwoEExfYu1WYNO5LuYmaJqNmmtlg2b9U8JPJp/oW",new ArrayList<>());
    }
}