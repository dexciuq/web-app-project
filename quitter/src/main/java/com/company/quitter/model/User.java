package com.company.quitter.model;


import com.company.quitter.model.enumiration.UserRole;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private UserRole userRole;
    @Indexed(unique = true)
    private String email;
    private String number;
    private String password;
    private String registrationDate;
    private Profile profile;
    private List<User> followers;
    private List<User> following;
    private List<Post> posts;

    public User(UserRole userRole,
                String email,
                String number,
                String password,
                String registrationDate,
                Profile profile,
                List<User> followers,
                List<User> following,
                List<Post> posts) {
        this.userRole = userRole;
        this.email = email;
        this.number = number;
        this.password = password;
        this.registrationDate = registrationDate;
        this.profile = profile;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
    }
}


