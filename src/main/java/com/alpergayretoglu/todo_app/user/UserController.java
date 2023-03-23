package com.alpergayretoglu.todo_app.user;

import com.alpergayretoglu.todo_app.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "{id}")
    public User getUser(@PathVariable("id") Long userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PutMapping(path = "{id}")
    public void updateUser(
            @PathVariable("id") Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password) {
        userService.updateUser(userId, name, surname, email, password);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("{userId}/task")
    public List<Task> getTasks(@PathVariable("userId") Long userId) {
        return userService.getOwnedTasks(userId);
    }

    @GetMapping("{userId}/task/{taskIndex}")
    public Task getTask(@PathVariable("userId") Long userId,
                        @PathVariable("taskIndex") Long taskIndex) {
        return userService.getOwnedTask(userId, taskIndex);
    }

    @PutMapping("{userId}/task")
    public void addTask(@PathVariable("userId") Long userId,
                        @RequestParam Long taskIndex) { // TODO: Find a better way
        userService.giveTask(userId, taskIndex);
    }

    @DeleteMapping("{userId}/task/{taskIndex}")
    public void deleteTask(@PathVariable("userId") Long userId,
                           @PathVariable("taskIndex") Long taskIndex) {
        userService.removeTask(userId, taskIndex);
    }

}