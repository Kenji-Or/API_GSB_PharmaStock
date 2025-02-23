package com.cours.api_gsbpharmastock.security;

import com.cours.api_gsbpharmastock.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole())); // Convertit le rôle en GrantedAuthority
    }

    @Override
    public String getPassword() {
        return user.getPassword();  // Retourne le mot de passe
    }

    @Override
    public String getUsername() {
        return user.getMail();  // Utilise l'email comme identifiant
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Compte non expiré
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Compte non verrouillé
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Credentials valides
    }

    @Override
    public boolean isEnabled() {
        return true;  // Compte activé
    }
}
