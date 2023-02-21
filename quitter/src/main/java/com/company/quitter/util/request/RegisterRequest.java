package com.company.quitter.util.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String name;
  private String surname;
  private String dob;
  private String email;
  private String username;
  private String password;
  private String phoneNumber;

}
