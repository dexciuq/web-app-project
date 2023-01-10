package com.company.quitter.model;

import lombok.Data;

@Data
public class Profile {
    private String name;
    private String surname;
    private String DOB;
    private String address;
    private String about;
    private String pictureURL;

    public Profile(String name, String surname, String DOB, String address) {
        this.name = name;
        this.surname = surname;
        this.DOB = DOB;
        this.address = address;
    }
}
