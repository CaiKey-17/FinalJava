package com.example.chuyentrang.repository;

import com.example.chuyentrang.model.Available;
import com.example.chuyentrang.model.Deposit;
import com.example.chuyentrang.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableRepository extends CrudRepository<Available, Integer> {
    List<Available> findByBroker(User broker);

}
