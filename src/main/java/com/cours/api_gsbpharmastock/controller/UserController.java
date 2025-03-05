package com.cours.api_gsbpharmastock.controller;
import com.cours.api_gsbpharmastock.repository.UserRepository;
import com.cours.api_gsbpharmastock.security.CustomUserDetails;
import com.cours.api_gsbpharmastock.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import com.cours.api_gsbpharmastock.dto.LoginRequest;
import com.cours.api_gsbpharmastock.model.User;
import com.cours.api_gsbpharmastock.security.Blacklist;
import com.cours.api_gsbpharmastock.security.JwtUtil;
import com.cours.api_gsbpharmastock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/user/userInfo")
    public ResponseEntity<?> getUserId(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.substring(7);
            Long userId = jwtUtil.extractUserId(jwt);
            Optional<User> userOptional = userService.getUserById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return ResponseEntity.ok().body(user); // Renvoie un JSON contenant l'utilisateur
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide");
        }
    }

    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping("/user/register")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user1 = userService.getUserByMail(loginRequest.getMail());

        if(user1.isPresent()) {
            User user = user1.get();
            if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                // Générer un JWT
                String token = jwtUtil.generateToken(user.getMail(), user.getId().toString(), Integer.toString(user.getRole()));

                // Retourner le token
                return ResponseEntity.ok().body(Map.of("token",token));
            } else {
                return ResponseEntity.status(401).body("Mot de passe incorrect");
            }
        } else {
            return ResponseEntity.status(404).body("Utilisateur non trouvé");
        }
    }

    @PostMapping("/user/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);  // Retirer "Bearer "

            // Ajout du token à la blacklist
            Blacklist.addToken(jwtToken);
        }

        return ResponseEntity.ok().body("Logout effectué");
    }

    @PatchMapping("/user/editUser")
    public ResponseEntity<?> editUser(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> updates) {
        try {
            String jwt = token.substring(7);
            Long userId = jwtUtil.extractUserId(jwt);
            Optional<User> u = userService.getUserById(userId);
            if (u.isPresent()) {
                User currentUser = u.get();
                boolean emailChanged = false;

                if (updates.containsKey("firstName")) {
                    currentUser.setFirstName((String) updates.get("firstName"));
                }
                if (updates.containsKey("lastName")) {
                    currentUser.setLastName((String) updates.get("lastName"));
                }
                if (updates.containsKey("mail")) {
                    currentUser.setMail((String) updates.get("mail"));
                    emailChanged = true;
                }
                if (updates.containsKey("password")) {
                    currentUser.setPassword((String) updates.get("password"));
                }

                userService.saveUser(currentUser);
                // Générer un nouveau token si l'email a changé
                String newToken = emailChanged ? jwtUtil.generateToken(currentUser.getMail(), currentUser.getId().toString(), Integer.toString(currentUser.getRole())) : null;
                // Construire la réponse JSON
                Map<String, Object> response = new HashMap<>();
                response.put("id", currentUser.getId());
                response.put("firstName", currentUser.getFirstName());
                response.put("lastName", currentUser.getLastName());
                response.put("mail", currentUser.getMail());
                response.put("role", currentUser.getRole());
                if (newToken != null) {
                    response.put("token", newToken);
                    // ✅ Supprimer l'ancien token (optionnel, si tu as un système de token store)
                    Blacklist.addToken(token);
                }
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalide");
        }
    }


    @DeleteMapping("/deleteuser/{id}")
    public void deleteUserById(@PathVariable("id") final Long id) {
        userService.deleteUserById(id);
    }
}
