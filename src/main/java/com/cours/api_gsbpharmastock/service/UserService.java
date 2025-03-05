package com.cours.api_gsbpharmastock.service;

import com.cours.api_gsbpharmastock.model.User;
import com.cours.api_gsbpharmastock.repository.UserRepository;
import com.cours.api_gsbpharmastock.security.JwtUtil;
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
    @Autowired
    private JwtUtil jwtUtil;

    public Optional<User> getUserById(final Long id) {
        return userRepository.findById(id);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByMail(final String mail) {
        return userRepository.findByMail(mail);
    }

    public Boolean validateToken(final String token, final String mail) {
        return jwtUtil.validateToken(token, mail);
    }

    public void deleteUserById(final Long id) {
        userRepository.deleteById(id);
    }

    public User saveUser(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            // Vérifie si le mot de passe a été modifié
            if (user.getId() == null || !userRepository.existsById(user.getId())) {
                // Si l'utilisateur n'a pas encore d'id (nouveau) ou si l'utilisateur n'existe pas en base
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                // Si l'utilisateur existe et que le mot de passe n'a pas changé
                User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
                if (!user.getPassword().equals(existingUser.getPassword())) {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
            }
        }
        User savedUser = userRepository.save(user);
        return savedUser;
    }
}
