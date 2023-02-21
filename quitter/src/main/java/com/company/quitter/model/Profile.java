package com.company.quitter.model;

import com.company.quitter.model.enumiration.Degree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private String name;
    private String surname;
    private String dob;
    private Degree degree;
    private String address;
    private String aboutMe;
    private String profilePictureURL;

    public Profile(String name, String surname, String dob) {
        this.name = name;
        this.surname = surname;
        this.dob = dob;
    }
}
