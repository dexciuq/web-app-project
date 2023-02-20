package com.company.quitter.controller;

import com.company.quitter.model.Post;
import com.company.quitter.model.User;
import com.company.quitter.service.PostService;
import com.company.quitter.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/sort")
    public List<Post> getSortedByField(@RequestParam(value = "field") String field) {
        return postService.getAllPosts(field);
    }

//    @GetMapping("/search")
//    public User getUserByUsername(@RequestParam(value = "username") String username) {
//        return userService.getUserByUsername(username);
//    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable String id) {
        return postService.getPostById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post addPost(@RequestBody Post post, Authentication authentication) {
        return postService.createPost(post, authentication.getName());
    }

    @PatchMapping("/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody Post post) {return postService.partialUpdatePost(id, post); }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable String id) {
        return postService.deletePost(id);
    }
}
