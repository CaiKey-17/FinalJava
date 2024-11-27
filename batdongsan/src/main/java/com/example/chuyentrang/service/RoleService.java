package com.example.chuyentrang.service;

import com.example.chuyentrang.model.Role;
import com.example.chuyentrang.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(String roleName) {
        Role role = new Role(roleName);
        return roleRepository.save(role);
    }

    public Role findByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
