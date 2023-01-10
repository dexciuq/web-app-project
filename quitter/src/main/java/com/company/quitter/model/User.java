package com.company.quitter.model;


import com.company.quitter.Main;
import com.company.quitter.model.enumiration.UserRole;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private UserRole userRole;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;
    private String phoneNumber;
    private String password;
    private String registrationDate;
    private Profile userProfile;
    @DBRef
    private List<User> followers;
    @DBRef
    private List<User> following;
    @DBRef
    private List<Post> posts;

    public User(UserRole userRole,
                String username,
                String email,
                String phoneNumber,
                String password,
                String registrationDate,
                Profile userProfile,
                List<User> followers,
                List<User> following,
                List<Post> posts) {
        this.userRole = userRole;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.registrationDate = registrationDate;
        this.userProfile = userProfile;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
    }
}


