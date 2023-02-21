package com.company.quitter.repository;

import com.company.quitter.model.Post;
import com.company.quitter.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    @Query("{'title': ?0}")
    List<Post> findByTitle(String title);

    @Query("{'tags': { $in: [?0] } }")
    List<Post> findByTag(String tag);


}
