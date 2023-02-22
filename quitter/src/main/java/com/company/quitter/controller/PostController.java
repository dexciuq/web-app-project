package com.company.quitter.controller;

import com.company.quitter.model.Comment;
import com.company.quitter.model.Post;
import com.company.quitter.model.User;
import com.company.quitter.service.CommentService;
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
    private final CommentService commentService;
    @GetMapping
    public ResponseEntity<?> getAllPosts(
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
        return ResponseEntity.ok(mongoTemplate.find(query, Post.class));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getPostsByTag(@RequestParam(value = "tag", required = false) String tagName,
                                    @RequestParam(value = "title", required = false) String titleName) {

        if (tagName != null) return ResponseEntity.ok(postService.getPostsByTag(tagName));

        return ResponseEntity.ok(postService.getPostsByTitle(titleName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable String id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<?> addPost(@RequestBody Post post, Authentication authentication) {
        return new ResponseEntity<>(postService.createPost(post, authentication.getName()), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable String id, @RequestBody Post post, Authentication authentication) {
        Post postToUpdate = postService.partialUpdatePost(id, post, authentication.getName());

        if (postToUpdate != null) return ResponseEntity.ok(postToUpdate);

        return new ResponseEntity<>("You are not the owner of this post.", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable String id, Authentication authentication) {
        return ResponseEntity.ok(postService.deletePost(id, authentication.getName()));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addComment(@PathVariable String id, @RequestBody Comment comment,
                                        Authentication authentication) {
        return new ResponseEntity<>(commentService.createComment(id, comment, authentication.getName()), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/{commentID}")
    public ResponseEntity<?> updateComment(@PathVariable String id, @PathVariable int commentID,
                                           @RequestBody Comment comment, Authentication authentication) {
        return new ResponseEntity<>(commentService.updateComment(id, commentID, comment, authentication.getName()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/{commentID}")
    public ResponseEntity<?> deleteComment(@PathVariable String id, @PathVariable int commentID,
                                           Authentication authentication) {
        return ResponseEntity.ok(commentService.deleteComment(id, commentID, authentication.getName()));
    }
}
