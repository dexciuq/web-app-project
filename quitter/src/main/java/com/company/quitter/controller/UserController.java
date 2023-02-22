package com.company.quitter.controller;

import com.company.quitter.model.User;
import com.company.quitter.service.TokenService;
import com.company.quitter.service.UserService;
import com.company.quitter.util.request.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final MongoTemplate mongoTemplate;
    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(
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
        return ResponseEntity.ok(mongoTemplate.find(query, User.class));
    }

    @PostMapping("/token")
    public ResponseEntity<?> getUserByToken(@RequestBody AuthenticationResponse response) {
        String username = tokenService.extractUsername(response.getToken());
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getUserByUsername(@RequestParam(value = "username") String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping("/follow/{username}")
    public ResponseEntity<?> followUser(@PathVariable String username,
                                        Authentication authentication) {
        return new ResponseEntity<>(userService.follow(authentication.getName(), username),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/unfollow/{username}")
    public ResponseEntity<?> unfollowUser(@PathVariable String username,
                                          Authentication authentication) {
        return new ResponseEntity<>(userService.unfollow(authentication.getName(), username),
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id,
                                        @RequestBody User user,
                                        Authentication authentication) {
        String authId = userService
                .getUserByUsername(authentication.getName())
                .get().getId();

        if (!authId.equals(id))
            return new ResponseEntity<>("You can modify only your account", HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id,
                                            Authentication authentication) {
        String authId = userService.
                getUserByUsername(authentication.getName())
                .get().getId();

        if (!authId.equals(id))
            return new ResponseEntity<>("You can delete only your account", HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(userService.delete(id));
    }
}
