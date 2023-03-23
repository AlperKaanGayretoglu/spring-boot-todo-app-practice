package com.alpergayretoglu.todo_app.task;

import com.alpergayretoglu.todo_app.user.User;
import com.alpergayretoglu.todo_app.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalStateException(
                        "task with id " + taskId + " does not exist"
                ));
    }

    public void createTask(Task task) {
        taskRepository.save(task);
    }

    @Transactional
    public void updateTask(Long taskId, ZonedDateTime due, Boolean completed, Long ownerId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalStateException(
                        "task with id " + taskId + " does not exist"
                ));

        if (due != null && !Objects.equals(task.getDue(), due)) {
            task.setDue(due);
        }

        if (completed != null && !Objects.equals(task.getCompleted(), completed)) {
            task.setCompleted(completed);
        }

        if (ownerId != null) {
            User newOwner = userRepository.findById(ownerId)
                    .orElseThrow(() -> new IllegalStateException(
                            "user with id " + ownerId + " does not exist"
                    ));
            if (!Objects.equals(task.getOwner(), newOwner)) {
                task.setOwner(newOwner);
            }
        }

    }

    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new IllegalStateException("task with id " + taskId + " does not exist");
        }
        taskRepository.deleteById(taskId);
    }

}