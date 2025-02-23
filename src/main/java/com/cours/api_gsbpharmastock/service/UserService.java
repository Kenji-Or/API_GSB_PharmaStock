package com.cours.api_gsbpharmastock.service;

import com.cours.api_gsbpharmastock.model.User;
import com.cours.api_gsbpharmastock.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> getUserById(final Long id) {
        return userRepository.findById(id);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByMail(final String mail) {
        return userRepository.findByMail(mail);
    }

    public void deleteUserById(final Long id) {
        userRepository.deleteById(id);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return savedUser;
    }
}
