package com.example.identityservice.config;

import com.example.identityservice.models.LoggedInUser;
import com.example.identityservice.models.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public String authenticateUserAndGetToken(LoginRequest loginRequest) {
        authenticate(loginRequest.getUserName(), loginRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserName());

        return generateToken(userDetails);
    }

    private String generateToken(UserDetails userDetails) {

        UserDetailsImpl loggedInUser = (UserDetailsImpl) userDetails;
        LoggedInUser jwtUser = new LoggedInUser();
        jwtUser.setEmail(loggedInUser.getUser().getEmail());
        jwtUser.setId(loggedInUser.getUser().getId());
        jwtUser.setRole(loggedInUser.getUser().getRole());
        jwtUser.setName(loggedInUser.getUser().getFirstName());
        final String token = jwtUtils.generateToken(loggedInUser);
        log.info("User {} authenticated", loggedInUser.getUser().getEmail());
        return token;
    }

    private void authenticate(String userName, String password) {
    try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
    } catch (AuthenticationException e) {
        throw new BadCredentialsException("Invalid Credentials");
    }
}
}
