package com.people.restdemo.controller;

import com.people.restdemo.security.CustomUserDetailsService;
import com.people.restdemo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin
/**
 * Get Endpoint to generate a token and give to client.
 *
 */
public class JWTAuthenticationController {

    public static final String TESTUSER = "testuser";
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseEntity<?> createAuthenticationToken() {
        // Generating a token with testuser passing the token to keep it simple for this demo.
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(TESTUSER);
        String token = jwtTokenProvider.createToken(userDetails.getUsername(), new ArrayList<>());
        return ResponseEntity.ok(token);
    }


}
