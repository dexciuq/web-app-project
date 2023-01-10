package com.company.quitter.service;

import com.company.quitter.Main;
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        user.setUserRole(UserRole.USER);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        user.setRegistrationDate(LocalDateTime.now().format(Main.dataFormatter));
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

    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return "User has been successfully by id " + id;
    }


}
