package com.example.chuyentrang.service;

import com.example.chuyentrang.model.Deposit;
import com.example.chuyentrang.model.User;
import com.example.chuyentrang.repository.DepositRepository;
import com.example.chuyentrang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DepositService {
    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private UserRepository userRepository;

    public Deposit saveDeposit(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    public Iterable<Deposit> getAllDeposits() {
        return depositRepository.findAll();
    }

    public void deleteDeposit(Deposit deposit) {
        depositRepository.delete(deposit);
    }

    public Deposit update(int id, Deposit updatedDeposit) {
        Optional<Deposit> existingDeposit = depositRepository.findById(id);
        if (existingDeposit.isPresent()) {
            Deposit deposit = existingDeposit.get();
            deposit.setTinhTrangThanhToan(updatedDeposit.getTinhTrangThanhToan());
            return depositRepository.save(deposit);
        }
        else {
            throw new RuntimeException("Deposit not found");
        }
    }

    public void createDeposit(Long userId, Double amount) throws Exception {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));


        if (amount <= 0) {
            throw new Exception("Invalid deposit amount");
        }


        user.setBalance((user.getBalance() != null ? user.getBalance() : 0) + amount);
        userRepository.save(user);


        Deposit deposit = new Deposit();
        deposit.setSoTien(amount);
        deposit.setNgayNap(LocalDateTime.now());
        deposit.setTinhTrangThanhToan("Đã thanh toán");
        deposit.setUser(user);


        depositRepository.save(deposit);
    }


    public List<Deposit> customer_history(Long userId) {
        return depositRepository.findByUserId(userId);
    }

}
