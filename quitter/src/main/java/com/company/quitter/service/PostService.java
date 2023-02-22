package com.company.quitter.service;

import com.company.quitter.Main;
import com.company.quitter.model.Post;
import com.company.quitter.model.User;
import com.company.quitter.repository.PostRepository;
import com.company.quitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;

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

    // Creates a new post, sets all the default values in the appropriate fields and adds the ObjectID to the DBRef list
    // of posts in the user model.
    public Post createPost(Post post, String username) {
        // Finding the user by the username.
        User user = userService.getUserByUsername(username).get();
        // Adding the owner based on the username string.
        post.setPostOwner(user);

        // Default like count is 0.
        post.setLikeCount(0);
        // Recording the creation date, which is now.
        post.setCreationDate(LocalDateTime.now().format(Main.dataFormatter));
        // Creating an empty array of comments.
        post.setComments(new ArrayList<>());

        // Saving the post in the MongoDB, now we have the ID and can add it to the array of posts in the User model.
        Post postToSave = postRepository.save(post);

        // Creating a new list of posts from the existing one.
        List<Post> newPostsList = user.getPosts();
        // Adding the current post.
        newPostsList.add(postToSave);
        // Updating the posts list in the user.
        user.setPosts(newPostsList);
        // Saving the user in the DB.
        userRepository.save(user);

        return post;
    }

    public Post getPostById(String id) {
        return postRepository.findById(id).get();
    }

    public List<Post> getPostsByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    public List<Post> getPostsByTag(String tag) {
        return postRepository.findByTag(tag);
    }

    public Post partialUpdatePost(String id, Post body, String email) {
        User user = userService.getUserByEmail(email).get();
        Post post = getPostById(id);

        if (!isOwner(user, post))
        {
            System.out.println("You are not the owner of the post.");
            return null;
        }

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

    public String deletePost(String id, String username) {
        User user = userService.getUserByUsername(username).get();

        Post postToDelete = getPostById(id);

        if (!isOwner(user, postToDelete)) return "You are not the owner of this post.";

        user.getPosts().remove(postToDelete);
        userRepository.save(user);

        postRepository.deleteById(id);
        return "Post has been successfully deleted by id " + id;
    }

    private boolean isOwner(User userToCheck, Post post) {
        return post.getPostOwner().equals(userToCheck);
    }
}
