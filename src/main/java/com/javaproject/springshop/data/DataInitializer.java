 package com.javaproject.springshop.data;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.javaproject.springshop.model.Role;
import com.javaproject.springshop.model.User;
import com.javaproject.springshop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", 
                "ROLE_USER");
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
        createDefaultUserIfNotExits();

    }

    private void createDefaultUserIfNotExits() {
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstname("The user");
            user.setLastname("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("12345558"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default ver user " + " created seccessfully");
        }
    }
    
    private void createDefaultAdminIfNotExists() {

        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 5; i <= 7; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstname("Admin");
            user.setLastname("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword((passwordEncoder.encode("1234555877777")));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin user" + " created seccessfully");
        }
    }
    
    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
        
    }

    

}
