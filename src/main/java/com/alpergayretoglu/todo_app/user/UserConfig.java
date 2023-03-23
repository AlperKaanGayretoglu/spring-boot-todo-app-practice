package com.alpergayretoglu.todo_app.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner initialUsers(UserRepository repository) {
        return args -> {
            User u1 = new User(
                    "Alper",
                    "Gayretoğlu",
                    "alperkaangayretoglu@gmail.com",
                    "12345"
            );

            User u2 = new User(
                    "Alper",
                    "G",
                    "alpergu@gmail.com",
                    "12345"
            );

            User u3 = new User(
                    "a",
                    "b",
                    "c@gmail.com",
                    "12345"
            );

            repository.saveAll(
                    List.of(u1, u2, u3)
            );
        };
    }
}
