package com.alpergayretoglu.todo_app.task;

import com.alpergayretoglu.todo_app.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1 // auto increment
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long index;
    private String context;
    private ZonedDateTime due;
    private Boolean completed;
    @ManyToOne
    @Nullable
    private User owner;

    public Task(String context, ZonedDateTime due, Boolean completed, User owner) {
        this.context = context;
        this.due = due;
        this.completed = completed;
        this.owner = owner;
    }
}
