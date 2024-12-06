package com.example.chuyentrang.repository;

import com.example.chuyentrang.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Query(value = "SELECT u.* FROM user u " +
            "JOIN user_roles ur ON u.id = ur.user_id " +
            "JOIN role r ON ur.role_id = r.id " +
            "WHERE r.role_name = 'ROLE_CUSTOMER'",
            nativeQuery = true)
    List<User> findCustomers();


}


