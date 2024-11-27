package com.example.chuyentrang.repository;

import com.example.chuyentrang.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}