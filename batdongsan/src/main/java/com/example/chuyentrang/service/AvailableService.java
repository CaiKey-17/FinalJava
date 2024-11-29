package com.example.chuyentrang.service;

import com.example.chuyentrang.model.Available;
import com.example.chuyentrang.repository.AvailableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
@Service
public class AvailableService {
    @Autowired
    private AvailableRepository availableRepository;

    public Available save(Available available) {
        return availableRepository.save(available);
    }

    public Iterable<Available> findAll() {
        return availableRepository.findAll();
    }

    public void delete(Available available) {
        availableRepository.delete(available);
    }

    public boolean canPost(Long brokerId) {
        Optional<Available> available = availableRepository.findByBroker_IdAndExpirationDateAfterAndQuantityAvailableGreaterThan(
                brokerId,
                LocalDateTime.now(),
                0
        );

        return available.isPresent();
    }

    public Available findById(int id) {
        return availableRepository.findById(id).get();
    }
}
