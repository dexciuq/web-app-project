package com.company.quitter.repository;

import com.company.quitter.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, Integer> {

  @Query("{'_id': ?0}")
  List<Token> findAllValidTokenByUser(String id);

  Optional<Token> findByToken(String token);
}
