package com.example.identityservice.config;

import com.example.identityservice.entity.User;
import com.example.identityservice.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailIgnoreCase(username);
        if (user.isPresent()) {
            UserDetailsImpl loggedInUser = new UserDetailsImpl();
            loggedInUser.setUser(user.get());
            return loggedInUser;
        } else {
            log.error("Error getting user for authentication. Email not found");
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }
}
