package com.alpergayretoglu.todo_app.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.List;

@Configuration
public class TaskConfig {

    @Bean
    CommandLineRunner initialTasks(TaskRepository repository) {
        return args -> {
            Task task1 = new Task(
                    "study spring boot",
                    ZonedDateTime.now(),
                    false,
                    null
            );

            repository.saveAll(
                    List.of(task1)
            );
        };
    }
}