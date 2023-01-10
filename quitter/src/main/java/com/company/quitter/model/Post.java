package com.company.quitter.model;

import com.company.quitter.Main;
import com.company.quitter.model.enumiration.UserRole;
import lombok.Data;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String title;
    private String description;
    private String imageURL;
    private List<Comment> comments;
    private List<String> tags;
    private int likeCount;
    private String creationDate;

    public Post(String id,
                String title,
                String description,
                String imageURL,
                List<Comment> comments,
                List<String> tags,
                int likeCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.comments = comments;
        this.tags = tags;
        this.likeCount = likeCount;
        this.creationDate = LocalDateTime.now().format(Main.dataFormatter);
    }
}