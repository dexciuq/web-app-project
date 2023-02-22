package com.company.quitter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follower {
    private String id;
    private String name;
    private String surname;
    private String username;
    private String email;
}
