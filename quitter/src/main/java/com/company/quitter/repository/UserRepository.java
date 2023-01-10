package com.company.quitter.repository;

import com.company.quitter.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{'username': ?0}")
    User findByUsername(String username);
    @Query("{'email': ?0}")
    User findByEmail(String email);
}
