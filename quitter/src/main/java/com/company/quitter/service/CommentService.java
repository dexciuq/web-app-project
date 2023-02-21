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
public class CommentService {

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

    public Post createPost(Post post, String email) {

        User user = userService.getUserByEmail(email);

        System.out.println(user.getUsername());
        System.out.println(post.getTitle());

        post.setLikeCount(0);
        post.setCreationDate(LocalDateTime.now().format(Main.dataFormatter));
        post.setComments(new ArrayList<>());

        // Saving the post in the MongoDB, now we have the ID and can add it to the array of posts in the User model.
        Post postToSave = postRepository.save(post);

        String postID = post.getId();

        List<Post> newPostsList = user.getPosts();

        newPostsList.add(postToSave);

        user.setPosts(newPostsList);

//        for (Post posted:user.getPosts()
//             ) {
//            System.out.println(posted.toString());
//        }

        userRepository.save(user);

        return postToSave;
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

    public String deletePost(String id, String email) {
        User user = userService.getUserByEmail(email);

        Post postToDelete = getPostById(id);
        user.getPosts().remove(postToDelete);
        userRepository.save(user);

        postRepository.deleteById(id);
        return "Post has been successfully deleted by id " + id;
    }


}
