package com.example.identityservice.setup;

import com.example.identityservice.entity.Role;
import com.example.identityservice.entity.User;
import com.example.identityservice.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FactoryShippedData implements CommandLineRunner {

    private static final String LOG_USERS = "Created default users Admin and test user {} {} ";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Value("${pride.default-user-password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        setUpUsers();
    }

    private void setUpUsers() {
        if (userRepository.findAll().isEmpty()) {
            List<User> users = new ArrayList<>();
            User adminuser = new User();
            adminuser.setFirstName("Keerthi");
            adminuser.setLastName("S");
            adminuser.setEmail("keerthi.s@yethi.in");
            adminuser.setIsActive(Boolean.TRUE);
            adminuser.setPassword(passwordEncoder.encode(password));
            adminuser.setRole(Role.ADMIN);
            users.add(adminuser);

            User tester = new User();
            tester.setFirstName("Gangadhara");
            tester.setLastName("s");
            tester.setEmail("user@yethi.in");
            tester.setIsActive(Boolean.TRUE);
            tester.setPassword(passwordEncoder.encode(password));
            tester.setRole(Role.USER);
            users.add(tester);
            userRepository.saveAll(users);

            log.info(LOG_USERS, users.get(0).getId(), users.get(1).getId());
        }

        log.info("Finished setting up factory shipped user");
    }
}
