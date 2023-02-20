package com.company.quitter.service;

import com.company.quitter.Main;
import com.company.quitter.model.Post;
import com.company.quitter.model.Profile;
import com.company.quitter.model.User;
import com.company.quitter.model.enumiration.UserRole;
import com.company.quitter.repository.PostRepository;
import com.company.quitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getAllPosts(String field) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (field.charAt(0) == '-') {
            direction = Sort.Direction.DESC;
            field = field.substring(1);
        }
        return postRepository.findAll(Sort.by(direction, field));
    }

    public Post createPost(Post post, String email) {

        User user = userService.getUserByEmail(email);

        List<Post> newPosts = user.getPosts();
        newPosts.add(post);
        user.setPosts(newPosts);

        post.setLikeCount(0);
        post.setCreationDate(LocalDateTime.now().format(Main.dataFormatter));
        post.setComments(new ArrayList<>());
        post.setTags(new HashSet<>());
        return postRepository.save(post);
    }

    public Post getPostById(String id) {
        return postRepository.findById(id).get();
    }

    public Post getPostByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    public List<Post> getPostsByTag(String tag) {
        return postRepository.findByTag(tag);
    }

    public Post partialUpdatePost(String id, Post body) {
        Post post = getPostById(id);

        if (body.getTitle() != null) post.setTitle(body.getTitle());
        if (body.getDescription() != null) post.setDescription(body.getDescription());
        if (body.getImageURL() != null) post.setImageURL(body.getImageURL());
        if (body.getTags() != null) {
            Set<String> newTags = body.getTags();
            Set<String> oldTags = post.getTags();
            oldTags.addAll(newTags);
        }
        return post;
    }

    public String deletePost(String id) {
        postRepository.deleteById(id);
        return "Post has been successfully deleted by id " + id;
    }


}
