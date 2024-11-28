package com.example.chuyentrang.repository;

import com.example.chuyentrang.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
public interface DepositRepository extends JpaRepository<Deposit, Integer> {
    List<Deposit> findByUserId(Long userId);
}
