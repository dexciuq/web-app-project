package com.company.quitter.controller;

import com.company.quitter.model.User;
import com.company.quitter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final MongoTemplate mongoTemplate;
    private final UserService userService;
    
    @GetMapping
    public List<User> getAllUsers(
            @RequestParam(name = "sort", required = false) String sortBy,
            @RequestParam(name = "direction", required = false) String sortDirection,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "page_size", required = false) Integer pageSize) {

        Query query = new Query();
        if (sortBy != null) {
            Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
            query.with(sort);
        }
        if (page != null && pageSize != null) {
            if (page < 1) page = 1;
            query.skip((page - 1) * pageSize);
            query.limit(pageSize);
        }
        return mongoTemplate.find(query, User.class);
    }

    @GetMapping("/search")
    public User getUserByUsername(@RequestParam(value = "username") String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        return userService.partialUpdateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}
