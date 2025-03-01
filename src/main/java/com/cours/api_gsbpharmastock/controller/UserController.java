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

    @GetMapping("/userId")
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

//    @GetMapping("/user/{id}")
//    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
//        Optional<User> user = userService.getUserById(id);
//        if (user.isPresent()) {
//            return ResponseEntity.ok().body(Map.of(
//                    "firstName", user.get().getFirstName(),
//                    "lastName", user.get().getLastName(),
//                    "email", user.get().getMail(),
//                    "role", user.get().getRole()
//            ));
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
//        }
//    }


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
