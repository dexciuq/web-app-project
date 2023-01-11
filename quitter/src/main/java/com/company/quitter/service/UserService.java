package com.company.quitter.service;

import com.company.quitter.Main;
import com.company.quitter.model.Profile;
import com.company.quitter.model.User;
import com.company.quitter.model.enumiration.UserRole;
import com.company.quitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final String defaultURL = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png";

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        user.setUserRole(UserRole.USER);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        user.setRegistrationDate(LocalDateTime.now().format(Main.dataFormatter));
        user.getUserProfile().setProfilePictureURL(defaultURL);
        user.setFollowers(new ArrayList<>());
        user.setFollowing(new ArrayList<>());
        user.setPosts(new ArrayList<>());
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User partialUpdateUser(String id, User body) {
        User user = getUserById(id);

        Profile profile = user.getUserProfile();
        Profile bodyProfile = body.getUserProfile();

        if (body.getEmail() != null) user.setEmail(body.getEmail());
        if (body.getPhoneNumber() != null) user.setPhoneNumber(body.getPhoneNumber());
        if (body.getPassword() != null) user.setPassword(body.getPassword());

        if (bodyProfile == null) {
            return user;
        }

        if (body.getUserProfile().getAboutMe() != null) profile.setAboutMe(bodyProfile.getAboutMe());
        if (body.getUserProfile().getName() != null) profile.setName(bodyProfile.getName());
        if (body.getUserProfile().getSurname() != null) profile.setSurname(bodyProfile.getSurname());
        if (body.getUserProfile().getDOB() != null) profile.setDOB(bodyProfile.getDOB());
        if (body.getUserProfile().getDegree() != null) profile.setDegree(bodyProfile.getDegree());
        if (body.getUserProfile().getAddress() != null) profile.setAddress(bodyProfile.getAddress());
        if (body.getUserProfile().getProfilePictureURL() != null) profile.setProfilePictureURL(bodyProfile.getProfilePictureURL());

            return user;
    }

    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return "User has been successfully by id " + id;
    }


}
