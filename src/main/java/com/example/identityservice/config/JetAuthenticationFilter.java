package com.example.identityservice.config;

import com.example.identityservice.entity.User;
import com.example.identityservice.repo.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class JetAuthenticationFilter  extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String autHeader = request.getHeader("Authorization");
        final String strippedToken;
        final String userEmail;
        if (autHeader == null || !autHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        strippedToken = autHeader.replace("Bearer ", "");
        if(jwtService.isTokenIsValid(strippedToken)) {
            userEmail = jwtService.extractUserName(strippedToken);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Optional<User> user = userRepository.findByEmailIgnoreCase(userEmail);
                if (user.isPresent()) {
                    List<GrantedAuthority> authorities = Collections
                            .singletonList(new SimpleGrantedAuthority(user.get().getRole().name()));
                    UsernamePasswordAuthenticationToken autToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
                    autToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(autToken);
                } else {
                    log.error("user is not available");
                }
            }
        }else {
            log.error("Error --> Access token has expired");
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
