package com.company.quitter.model;

import com.company.quitter.model.enumiration.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tokens")
public class Token {

  @Id
  public String id;
  @Indexed(unique = true)
  public String token;
  public TokenType tokenType = TokenType.BEARER;
  public boolean revoked;
  public boolean expired;
  public String userId;
}
