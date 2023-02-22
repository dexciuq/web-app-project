package com.company.quitter.model;

import com.company.quitter.Main;
import com.company.quitter.model.enumiration.UserRole;
import com.company.quitter.util.UserSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Comment {
    private int id;
    @DBRef
    @JsonSerialize(using = UserSerializer.class)
    private User commentOwner;
    private String text;
    private int likeCount;
    private String creationDate;

    public Comment(int id,
                   User commentOwner,
                   String text,
                   int likeCount) {
        this.id = id;
        this.commentOwner = commentOwner;
        this.text = text;
        this.likeCount = likeCount;
        this.creationDate = LocalDateTime.now().format(Main.dataFormatter);
    }

    public boolean equals(User user) {
        return this.commentOwner.getId().equals(user.getId());
    }
}