package com.alpergayretoglu.todo_app.user;

import com.alpergayretoglu.todo_app.task.Task;
import com.alpergayretoglu.todo_app.task.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public UserService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return getUserWithException(userId);
    }

    public void createUser(User user) {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        userRepository.save(user);
    }

    @Transactional // setters can now change the data in the DB
    public void updateUser(Long userId, String name, String surname, String email, String password) {
        User user = getUserWithException(userId);

        if (hasStringChanged(name, user.getName())) {
            user.setName(name);
        }

        if (hasStringChanged(surname, user.getSurname())) {
            user.setSurname(surname);
        }

        if (hasStringChanged(email, user.getEmail())) {
            Optional<User> userOptional = userRepository.findUserByEmail(email);
            if (userOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            user.setEmail(email);
        }

        if (hasStringChanged(password, user.getPassword())) {
            user.setPassword(password);
        }

    }

    private boolean hasStringChanged(String newString, String oldString) {
        return newString != null && newString.length() > 0 && !Objects.equals(oldString, newString);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalStateException("user with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    public List<Task> getOwnedTasks(Long userId) {
        return getUserWithException(userId).getTasks();
    }

    public Task getOwnedTask(Long userId, Long taskIndex) {
        User user = getUserWithException(userId);
        return user.getTasks().stream()
                .filter((task -> task.getIndex().equals(taskIndex)))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "task with index " + taskIndex + " does not exist"
                ));
    }

    @Transactional // setters can now change the data in the DB
    public void giveTask(Long userId, Long taskIndex) {
        User user = getUserWithException(userId);
        Task task = getTaskWithException(taskIndex);

        System.out.println(user);
        System.out.println(task);


        task.setOwner(user);

        System.out.println(task);

    }

    @Transactional // setters can now change the data in the DB
    public void removeTask(Long userId, Long taskIndex) {
        User user = getUserWithException(userId);
        Task task = getTaskWithException(taskIndex);
        if (!user.getTasks().remove(task)) {
            throw new IllegalStateException("user with id " + userId +
                    " does not have a task with index" + taskIndex);
        }
        task.setOwner(null);
    }

    private User getUserWithException(Long userId) throws IllegalStateException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "user with id " + userId + " does not exist"
                ));
    }

    private Task getTaskWithException(Long taskIndex) throws IllegalStateException {
        return taskRepository.findById(taskIndex)
                .orElseThrow(() -> new IllegalStateException(
                        "task with index " + taskIndex + " does not exist"
                ));
    }

}
