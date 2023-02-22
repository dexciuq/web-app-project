package com.company.quitter.service;

import com.company.quitter.model.Follower;
import com.company.quitter.model.Profile;
import com.company.quitter.model.User;
import com.company.quitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> partialUpdateUser(String id, User body) {
        Optional<User> optional = getUserById(id);
        if (optional.isEmpty())
            return Optional.empty();

        User user = optional.get();
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
        return Optional.of(user);
    }

    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return "User has been successfully by id " + id;
    }

    public String follow(String username, String followUsername) {
        User currUser = userRepository.findByUsername(username).get();
        Optional<User> userToFollow = userRepository.findByUsername(followUsername);

        if (userToFollow.isEmpty())
            return "This user has been deleted or wrong username";

        User user = userToFollow.get();
        currUser.getFollowers().add(Follower.builder()
                .id(user.getId())
                .name(user.getUserProfile().getName())
                .surname(user.getUserProfile().getSurname())
                .email(user.getEmail())
                .username(user.getUsername())
                .build());

        user.getFollowing().add(Follower.builder()
                .id(currUser.getId())
                .name(currUser.getUserProfile().getName())
                .surname(currUser.getUserProfile().getSurname())
                .email(currUser.getEmail())
                .username(currUser.getUsername())
                .build());

        userRepository.save(currUser);
        userRepository.save(user);

        return "User with username " + user.getUsername() + " was successfully add to your following list";
    }
}
