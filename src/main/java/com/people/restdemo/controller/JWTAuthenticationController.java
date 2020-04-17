package com.people.restdemo.controller;

import com.people.restdemo.security.CustomUserDetailsService;
import com.people.restdemo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin

public class JWTAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseEntity<?> createAuthenticationToken() throws Exception {

        authenticate("testuser", "testpassword");

        final UserDetails userDetails =  customUserDetailsService.loadUserByUsername("testuser");
        String token = jwtTokenProvider.createToken(userDetails.getUsername(),new ArrayList<>());
        return ResponseEntity.ok(token);


    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
