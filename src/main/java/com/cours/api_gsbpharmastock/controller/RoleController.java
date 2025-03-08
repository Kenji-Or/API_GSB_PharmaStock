package com.cours.api_gsbpharmastock.controller;

import com.cours.api_gsbpharmastock.exception.UnauthorizedException;
import com.cours.api_gsbpharmastock.model.Role;
import com.cours.api_gsbpharmastock.security.JwtUtil;
import com.cours.api_gsbpharmastock.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint to get roles
    @GetMapping("/roles")
    public Iterable<Role> getRoles(@RequestHeader("Authorization") String token) {
        // Token validation (you might want to handle different cases here)
        String jwt = token.substring(7);
        if (jwtUtil.isTokenValid(jwt)) {
            return roleService.getAllRoles();
        } else {
            throw new UnauthorizedException("Invalid or expired token");
        }
    }
}
