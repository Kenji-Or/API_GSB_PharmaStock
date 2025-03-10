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

    public User saveUser(User user, String newPassword) {
        if (user.getId() != null && userRepository.existsById(user.getId())) {
            // Récupération de l'utilisateur existant
            User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Si aucun mot de passe n'est fourni, on garde l'ancien mot de passe
            if (newPassword == null || newPassword.isEmpty()) {
                System.out.println("Aucun nouveau mot de passe fourni, on garde l'ancien hash.");
                user.setPassword(existingUser.getPassword());
            } else {
                // Si le mot de passe a changé, on le re-hashe
                if (!passwordEncoder.matches(newPassword, existingUser.getPassword())) {
                    System.out.println("Mot de passe modifié, on le hash.");
                    user.setPassword(passwordEncoder.encode(newPassword)); // Re-hash uniquement si modifié
                } else {
                    System.out.println("Le mot de passe est identique, on garde l'ancien hash.");
                    user.setPassword(existingUser.getPassword()); // Garde l'ancien hash si inchangé
                }
            }
        } else {
            // Nouvel utilisateur, on hash toujours le mot de passe
            System.out.println("Nouveau compte, hashing du mot de passe...");
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        return userRepository.save(user);
    }


}
