package com.cours.api_gsbpharmastock.service;

import com.cours.api_gsbpharmastock.model.Role;
import com.cours.api_gsbpharmastock.repository.RoleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
