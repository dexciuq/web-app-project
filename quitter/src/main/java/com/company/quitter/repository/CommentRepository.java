package com.company.quitter.repository;

import com.company.quitter.model.Comment;
import com.company.quitter.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

}
