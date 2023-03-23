package com.alpergayretoglu.todo_app.user;

import com.alpergayretoglu.todo_app.task.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users") // since the name "user" is reserved in PostgreSQL, we must use the name "users"
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1 // auto increment
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    @OneToMany
    private List<Task> tasks;

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
