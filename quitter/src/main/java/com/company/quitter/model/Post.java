package com.company.quitter.model;

import com.company.quitter.util.UserSerializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    @DBRef
    @JsonSerialize(using = UserSerializer.class)
    private User postOwner;
    private String title;
    private String description;
    private String imageURL;
    private List<Comment> comments;
    private Set<String> tags;
    private int likeCount;
    private String creationDate;

    public Post(String id,
                User postOwner,
                String title,
                String description,
                String imageURL,
                List<Comment> comments,
                Set<String> tags,
                String creationDate,
                int likeCount) {
        this.id = id;
        this.postOwner = postOwner;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.comments = comments;
        this.tags = tags;
        this.likeCount = likeCount;
        this.creationDate = creationDate;
    }
}