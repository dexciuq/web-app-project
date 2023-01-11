package com.company.quitter.controller;

import com.company.quitter.model.User;
import com.company.quitter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {return userService.partialUpdateUser(id, user); }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}
