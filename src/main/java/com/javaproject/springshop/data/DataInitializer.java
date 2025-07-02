 package com.javaproject.springshop.data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.javaproject.springshop.model.User;
import com.javaproject.springshop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultIfNotExits();
    }

    private void createDefaultIfNotExits() {
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstname("The user");
            user.setLastname("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword("12345558");
            userRepository.save(user);
            System.out.println("Default ver user " + " created seccessfully");
        }
    }

    

}
