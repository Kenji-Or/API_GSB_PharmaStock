package com.cours.api_gsbpharmastock.security;

import com.cours.api_gsbpharmastock.model.User;
import com.cours.api_gsbpharmastock.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userService.getUserByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + mail));


        return org.springframework.security.core.userdetails.User
                .withUsername(user.getMail())
                .password(user.getPassword())  // Doit être un mot de passe haché !
                .authorities(new SimpleGrantedAuthority(user.getRole())) // 🔥 Utilise authorities() au lieu de roles()
                .build();

    }
}
