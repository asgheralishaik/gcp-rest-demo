package com.people.restdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if ("sometestuser".equals(username)) {
//
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
        return new User(username,"$2y$12$QnBKFHfiEi.LjjwoEExfYu1WYNO5LuYmaJqNmmtlg2b9U8JPJp/oW",new ArrayList<>());
    }
}