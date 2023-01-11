package com.company.quitter.service;

import com.company.quitter.Main;
import com.company.quitter.model.User;
import com.company.quitter.model.enumiration.UserRole;
import com.company.quitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
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

    public List<User> getAllUsers(String field) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (field.charAt(0) == '-') {
            direction = Sort.Direction.DESC;
            field = field.substring(1);
        }
        return userRepository.findAll(Sort.by(direction, field));
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

    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return "User has been successfully by id " + id;
    }


}
