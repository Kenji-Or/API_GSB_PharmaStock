package com.cours.api_gsbpharmastock.controller;
import jakarta.servlet.http.HttpServletRequest;
import com.cours.api_gsbpharmastock.dto.LoginRequest;
import com.cours.api_gsbpharmastock.model.User;
import com.cours.api_gsbpharmastock.security.Blacklist;
import com.cours.api_gsbpharmastock.security.JwtUtil;
import com.cours.api_gsbpharmastock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") final Long id) {
        return userService.getUserById(id).orElse(null);
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
                String token = jwtUtil.generateToken(user.getMail());

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

    @PutMapping("/edituser/{id}")
    public User editUser(@PathVariable("id") final Long id, @RequestBody User user) {
        Optional<User> u = userService.getUserById(id);
        if(u.isPresent()) {
            User currentUser = u.get();

            String firstName = user.getFirstName();
            if(firstName != null) {
                currentUser.setFirstName(firstName);
            }
            String lastName = user.getLastName();
            if(lastName != null) {
                currentUser.setLastName(lastName);;
            }
            String mail = user.getMail();
            if(mail != null) {
                currentUser.setMail(mail);
            }
            String password = user.getPassword();
            if(password != null) {
                currentUser.setPassword(password);;
            }
            userService.saveUser(currentUser);
            return currentUser;
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteuser/{id}")
    public void deleteUserById(@PathVariable("id") final Long id) {
        userService.deleteUserById(id);
    }
}
