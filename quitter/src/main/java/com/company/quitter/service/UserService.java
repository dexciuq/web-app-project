package com.company.quitter.service;

import com.company.quitter.Main;
import com.company.quitter.model.Post;
import com.company.quitter.model.Profile;
import com.company.quitter.model.User;
import com.company.quitter.model.enumiration.UserRole;
import com.company.quitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        user.setUserRole(UserRole.USER);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        user.setRegistrationDate(LocalDateTime.now().format(Main.dataFormatter));
        String defaultURL = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";
        if (user.getUserProfile().getProfilePictureURL() == null) user.getUserProfile().setProfilePictureURL(defaultURL);
        user.setFollowers(new ArrayList<>());
        user.setFollowing(new ArrayList<>());
        user.setPosts(new ArrayList<>());
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public User partialUpdateUser(String id, User body) {
        User user = getUserById(id);
        Profile profile = user.getUserProfile();
        Profile bodyProfile = body.getUserProfile();
        if (body.getEmail() != null) user.setEmail(body.getEmail());
        if (body.getPhoneNumber() != null) user.setPhoneNumber(body.getPhoneNumber());
        if (body.getPassword() != null) user.setPassword(body.getPassword());
        if (bodyProfile != null) {
            if (bodyProfile.getAboutMe() != null) profile.setAboutMe(bodyProfile.getAboutMe());
            if (bodyProfile.getName() != null) profile.setName(bodyProfile.getName());
            if (bodyProfile.getSurname() != null) profile.setSurname(bodyProfile.getSurname());
            if (bodyProfile.getDob() != null) profile.setDob(bodyProfile.getDob());
            if (bodyProfile.getDegree() != null) profile.setDegree(bodyProfile.getDegree());
            if (bodyProfile.getAddress() != null) profile.setAddress(bodyProfile.getAddress());
            if (bodyProfile.getProfilePictureURL() != null) profile.setProfilePictureURL(bodyProfile.getProfilePictureURL());
        }
        userRepository.save(user);
        return user;
    }

    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return "User has been successfully by id " + id;
    }
}
