package com.example.identityservice.api;

import com.example.identityservice.config.AuthenticationService;
import com.example.identityservice.models.LoginRequest;
import com.example.identityservice.models.LoginResponse;
import com.example.identityservice.models.UserRequest;
import com.example.identityservice.models.UserResponse;
import com.example.identityservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request){
        return ResponseEntity.ok().body(userService.createUser(request));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping("/public/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request){
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(authenticationService.authenticateUserAndGetToken(request));
        return ResponseEntity.ok().body(loginResponse);
    }
}
