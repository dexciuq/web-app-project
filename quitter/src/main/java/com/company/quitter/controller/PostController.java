package com.company.quitter.controller;

import com.company.quitter.model.Post;
import com.company.quitter.model.User;
import com.company.quitter.service.PostService;
import com.company.quitter.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final MongoTemplate mongoTemplate;
    private final PostService postService;
    @GetMapping
    public List<Post> getAllPosts(
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
        return mongoTemplate.find(query, Post.class);
    }

    @GetMapping("/sort")
    public List<Post> getSortedByField(@RequestParam(value = "field") String field) {
        return postService.getAllPosts(field);
    }

    @GetMapping("/search")
    public List<Post> getPostsByTag(@RequestParam(value = "tag", required = false) String tagName,
                                    @RequestParam(value = "title", required = false) String titleName) {

        if (tagName != null) return postService.getPostsByTag(tagName);
        else return postService.getPostsByTitle(titleName);
    }

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
    public ResponseEntity<?> updatePost(@PathVariable String id, @RequestBody Post post, Authentication authentication) {
        Post postToUpdate = postService.partialUpdatePost(id, post, authentication.getName());

        if (postToUpdate != null) return ResponseEntity.ok(postToUpdate);

        return new ResponseEntity<>("You are not the owner of this post.", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable String id, Authentication authentication) {
        return postService.deletePost(id, authentication.getName());
       // return postService.deletePost(id, "basswallace@eargo.com");
    }
}
