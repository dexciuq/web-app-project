package com.company.quitter.model;

import com.company.quitter.Main;
import com.company.quitter.model.enumiration.UserRole;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private List<Comment> comments;
    // @DBRef
    private String userID;
    private String text;
    private int likeCount;
    private String creationDate;

    public Comment(String id,
                   List<Comment> comments,
                   String userID,
                   String text,
                   int likeCount) {
        this.id = id;
        this.comments = comments;
        this.userID = userID;
        this.text = text;
        this.likeCount = likeCount;
        this.creationDate = LocalDateTime.now().format(Main.dataFormatter);
    }
}