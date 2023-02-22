package com.company.quitter.service;

import com.company.quitter.Main;
import com.company.quitter.model.Comment;
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
    private final UserService userService;
    private final UserRepository userRepository;
    private final PostService postService;
    private final PostRepository postRepository;

    public Comment createComment(String id, Comment comment, String username) {
        // Default like count is 0.
        comment.setLikeCount(0);
        // Recording the creation date.
        comment.setCreationDate(LocalDateTime.now().format(Main.dataFormatter));
        // Comment owner.
        comment.setCommentOwner(userService.getUserByUsername(username).get());
        // Get the post by the id.
        Post post = postService.getPostById(id);
        // Set the comment id using the array's size.
        comment.setId(post.getComments().size() + 1);
        // Add the comment to the array of comments.
        post.getComments().add(comment);
        postRepository.save(post);
        return comment;
    }

    public Comment updateComment(String id, int commentID, Comment comment, String username) {
        User user = userService.getUserByUsername(username).get();
        Post post = postService.getPostById(id);

        Comment commentToUpdate = post.getComments().get(commentID - 1);

        if(!isOwner(user, commentToUpdate))
        {
            System.out.println("You are not the owner of the comment.");
            return null;
        }

        commentToUpdate.setText(comment.getText());
        postRepository.save(post);
        return commentToUpdate;
    }

    public String deleteComment(String id, int commentID, String username) {
        User user = userService.getUserByUsername(username).get();
        Post post = postService.getPostById(id);

        Comment commentToDelete = post.getComments().get(commentID - 1);

        if(!isOwner(user, commentToDelete)) return "You are not the owner of the comment.";

        post.getComments().remove(commentToDelete);
        postRepository.save(post);
        return "Comment has been successfully deleted by id " + commentID;
    }

    private boolean isOwner(User userToCheck, Comment comment) {
        return comment.getCommentOwner().equals(userToCheck);
    }
}
