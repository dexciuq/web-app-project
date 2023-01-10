package com.company.quitter.model;

import com.company.quitter.Main;
import com.company.quitter.model.enumiration.Degree;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Profile {
    private final String defaultURL = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
    private String name;
    private String surname;
    private String DOB;
    private Degree degree;
    private String address;
    private String aboutMe;
    private String profilePictureURL;

    public Profile(String name, String surname, LocalDateTime DOB, String address) {
        this.name = name;
        this.surname = surname;
        this.DOB = DOB.format(Main.dataFormatter);
        this.address = address;
        this.profilePictureURL = defaultURL;
    }
}
